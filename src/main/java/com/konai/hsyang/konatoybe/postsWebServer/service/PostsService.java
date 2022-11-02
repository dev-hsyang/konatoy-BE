package com.konai.hsyang.konatoybe.postsWebServer.service;

import com.konai.hsyang.konatoybe.exceptions.NoLocationFoundException;
import com.konai.hsyang.konatoybe.exceptions.NoPostsFoundException;
import com.konai.hsyang.konatoybe.exceptions.NoUserFoundException;
import com.konai.hsyang.konatoybe.locationWebServer.repository.LocationRepository;
import com.konai.hsyang.konatoybe.loginWebServer.repository.UserRepository;
import com.konai.hsyang.konatoybe.postsWebServer.domain.Posts;
import com.konai.hsyang.konatoybe.postsWebServer.dto.*;
import com.konai.hsyang.konatoybe.postsWebServer.etc.FileNameModel;
import com.konai.hsyang.konatoybe.postsWebServer.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){

        requestDto.init();
        return postsRepository.save(requestDto.toEntity()).getPostsID();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){

        requestDto.setLocation(locationRepository.findById(requestDto.getLocationID()).orElseThrow(() -> new NoLocationFoundException()));
        postsRepository.findById(id).orElseThrow(()-> new NoPostsFoundException()).update(requestDto);
        return id;
    }

    @Transactional
    public Long delete(Long id){

        Posts entity = postsRepository.findById(id).orElseThrow(() -> new NoPostsFoundException());
        locationRepository.delete(locationRepository.findById(entity.getLocation().getLocationID()).orElseThrow(() -> new NoLocationFoundException()));
        postsRepository.delete(entity);
        return id;
    }

    public Posts findById(Long id) {

        return postsRepository.findById(id).orElseThrow(() -> new NoPostsFoundException());
    }

    public PostsResponseDto postsResponseDtoFindById(Long id){

        return new PostsResponseDto(postsRepository.findById(id).orElseThrow(() -> new NoPostsFoundException()));
    }

    @Transactional
    public List<PostsListResponseDto> findAllDescCurrent(){

        return postsRepository
                .findAllDescCurrent()
                .stream()
                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostsListResponseDto> findAllDescHits(){

        return postsRepository
                .findAllDescHits()
                .stream()
                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostsListResponseDto> findAllDescLikes(){

        return postsRepository
                .findAllDescLikes()
                .stream()
                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostsListResponseDto> findAllDescById(Long userID){

        return postsRepository
                .findAllDescByUser(userID)
                .stream()
                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateHits(Long id){

        postsRepository.updateHits(id);
    }

    @Transactional
    public void deleteAllByUserId(Long userID){

        postsRepository.deleteAllByUser(userID);
    }

    public boolean isPostAuthor(Long id, PostsResponseDto dto){

        return id.equals(dto.getUser().getUserID()) ? true : false;
    }

    public List<PostsListResponseDto> sort(String sortType){

        switch (sortType) {
            case "date": return this.findAllDescCurrent();
            case "hits": return this.findAllDescHits();
            case "likes": return this.findAllDescLikes();
            default: return null;
        }
    }

    public void setPostAuthor(PostsSaveRequestDto requestDto, Long userID){

        requestDto.setAuthor(userRepository.findById(userID).orElseThrow(() -> new NoUserFoundException()));
    }

    public void setLocation(PostsSaveRequestDto requestDto, Long locationID){

        requestDto.setLocation(locationRepository.findById(locationID).orElseThrow(() -> new NoLocationFoundException()));
    }

    public Page<PostsListResponseDto> getPage(PageRequestDto requestDto, Pageable pageable){

        if(requestDto==null)
            requestDto.setPageDefault();
        return postsRepository.findAllV2(requestDto, pageable);
    }

    public PostsImageResponseDto uploadImage(MultipartFile multi){

        PostsImageResponseDto responseDto = new PostsImageResponseDto();
        try {
            String uploadPath = "D:/konaToy_images";
            String originFileName = multi.getOriginalFilename();
            String extName = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());
            long size = multi.getSize();
            FileNameModel fileNameModel = new FileNameModel();
            String saveFileName = fileNameModel.GenSaveFileName(extName);
            responseDto.setFilename(saveFileName);
            if(!multi.isEmpty()) {
                File file = new File(uploadPath, saveFileName);
                multi.transferTo(file);
            }
        } catch (Exception e) {
            System.out.println("[ERROR]" + e.getMessage());
        }
        return responseDto;
    }

    @Transactional
    public ResponseEntity<PostsResponseDto> viewPost(Long postID, HttpServletRequest request, HttpServletResponse response){
        PostsResponseDto responseDto = new PostsResponseDto(postsRepository.getById(postID));
        HttpHeaders headers = new HttpHeaders();

        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("postView")){
                    oldCookie = cookie;
                }
            }
        }
        if(oldCookie!=null){
            if(!oldCookie.getValue().contains("["+postID.toString()+"]")){
                updateHits(postID);
                oldCookie.setValue(oldCookie.getValue() + "[" + postID + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        }else {
            updateHits(postID);
            Cookie newCookie = new Cookie("postView", "[" + postID + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
            System.out.println(newCookie);
        }
        return new ResponseEntity<PostsResponseDto>(responseDto, headers, HttpStatus.OK);
    }
}
