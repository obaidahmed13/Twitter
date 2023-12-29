package com.obaidahmed.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obaidahmed.twitter.dto.TweetDto;
import com.obaidahmed.twitter.exception.UserException;
import com.obaidahmed.twitter.mapper.TweetDtoMapper;
import com.obaidahmed.twitter.model.Tweet;
import com.obaidahmed.twitter.model.User;
import com.obaidahmed.twitter.request.TweetReplyRequest;
import com.obaidahmed.twitter.response.ApiResponse;
import com.obaidahmed.twitter.service.TweetService;
import com.obaidahmed.twitter.service.UserService;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
	
	@Autowired
	private TweetService tweetService;
	
	@Autowired
	private UserService userService;
	
	// Data transfer object
	@PostMapping("/create")
	public ResponseEntity<TweetDto> createTweet(@RequestBody Tweet req, 
			@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		
		Tweet tweet = tweetService.createTweet(req, user);
		
		TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		
		return new ResponseEntity<>(tweetDto, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/reply")
	public ResponseEntity<TweetDto> replyTweet(@RequestBody TweetReplyRequest req, 
			@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		
		Tweet tweet = tweetService.createReply(req, user);
		
		TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		
		return new ResponseEntity<>(tweetDto, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{tweetId}/retweet")
	public ResponseEntity<TweetDto> retweet(@PathVariable Long tweetId, 
			@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		
		Tweet tweet = tweetService.retweet(tweetId, user);
		
		TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		
		return new ResponseEntity<>(tweetDto, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/{tweetId}")
	public ResponseEntity<TweetDto> findTweetById(@PathVariable Long tweetId, 
			@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		
		Tweet tweet = tweetService.findById(tweetId);
		
		TweetDto tweetDto = TweetDtoMapper.toTweetDto(tweet, user);
		
		return new ResponseEntity<>(tweetDto, HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/{tweetId}/delete")
	public ResponseEntity<ApiResponse> deleteTweet(@PathVariable Long tweetId, 
			@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		
		tweetService.deleteTweetById(tweetId, user.getId());
		
		ApiResponse res = new ApiResponse();
		res.setMessage("Deleted Tweet Successfully");
		res.setStatus(true);
		
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TweetDto>> getAllTweets( 
			@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Tweet> tweets= tweetService.findAllTweets();
		
		List<TweetDto> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
		
		return new ResponseEntity<>(tweetDtos, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TweetDto>> getUsersTweets(@PathVariable Long userId, 
			@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Tweet> tweets= tweetService.getUserTweet(user);
		
		List<TweetDto> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
		
		return new ResponseEntity<>(tweetDtos, HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/user/{userId}/likes")
	public ResponseEntity<List<TweetDto>> findTweetsByUserLikes(@PathVariable Long userId, 
			@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Tweet> tweets= tweetService.findByLikesContainsUser(user);
		
		List<TweetDto> tweetDtos = TweetDtoMapper.toTweetDtos(tweets, user);
		
		return new ResponseEntity<>(tweetDtos, HttpStatus.ACCEPTED);
		
	}


}
