package ru.saubulprojects.sausocial.service;

import org.springframework.data.domain.Page;

import ru.saubulprojects.sausocial.dto.SubredditDTO;
import ru.saubulprojects.sausocial.entity.Subreddit;

public interface SubredditService {
	
	Subreddit saveSubreddit(SubredditDTO subredditDTO);

	Page<SubredditDTO> getSubredditPage(int pageNo);
	
	Subreddit findSubredditById(Long id);

	Subreddit findSubredditByName(String subredditName);
}
