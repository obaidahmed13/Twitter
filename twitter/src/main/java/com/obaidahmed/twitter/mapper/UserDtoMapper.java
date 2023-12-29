package com.obaidahmed.twitter.mapper;

import java.util.ArrayList;
import java.util.List;

import com.obaidahmed.twitter.dto.UserDto;
import com.obaidahmed.twitter.model.User;

public class UserDtoMapper {
	
	public static UserDto toUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setFullName(user.getFullName());
		userDto.setImage(user.getImage());
		userDto.setBackgroundImage(user.getBackgroundImage());
		userDto.setBio(user.getBio());
		userDto.setBirthDate(user.getBirthDate());
		userDto.setFollowers(toUserDtos(user.getFollowers()));
		userDto.setFollowing(toUserDtos(user.getFollowings()));
		userDto.setLogin_with_google(user.isLogin_with_google());
		userDto.setLocation(user.getLocation());
		
		return userDto;
	}

	private static List<UserDto> toUserDtos(List<User> followers) {
		List<UserDto>userDtos=new ArrayList<>();
		for(User user: followers) {
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setEmail(user.getEmail());
			userDto.setFullName(user.getFullName());
			userDto.setImage(user.getImage());
			userDtos.add(userDto);
		}
		return userDtos;
	}

}
