package ru.saubulprojects.sausocial.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.saubulprojects.sausocial.dto.SubredditDTO;
import ru.saubulprojects.sausocial.entity.Subreddit;
import ru.saubulprojects.sausocial.exception.SausocialException;
import ru.saubulprojects.sausocial.repository.SubredditRepository;
import ru.saubulprojects.sausocial.service.SubredditService;
import ru.saubulprojects.sausocial.service.UserService;

@Service
@RequiredArgsConstructor
public class SubredditServiceImpl implements SubredditService {
	
	private final SubredditRepository subredditRepo;
	private final UserService userService;
	
	@Override
	@Transactional
	public Subreddit saveSubreddit(SubredditDTO subredditDTO) {
		Subreddit subreddit = Subreddit.builder()
										   .name(subredditDTO.getName())
										   .description(subredditDTO.getDescription())
										   .user(userService.findUserByUsername(subredditDTO.getUsername()))
									   .build();
		return subredditRepo.save(subreddit);
	}

	@Override
	@Transactional
	public Page<SubredditDTO> getSubredditPage(int pageNo) {
		Pageable pageable = PageRequest.of(pageNo, 10);
		return new PageImpl<>(subredditRepo.findAll(pageable).stream().map(subreddit -> {return this.buildSubredditDTO(subreddit);}).collect(Collectors.toList()));
	}

	@Override
	public Subreddit findSubredditById(Long id) {
		Optional<Subreddit> subredditOptional = subredditRepo.findById(id);
		Subreddit subreddit = subredditOptional.orElseThrow(() -> new SausocialException(String.format("Subreddit with ID: %s not found.", id)));
		return subreddit;
	}

	@Override
	public Subreddit findSubredditByName(String subredditName) {
		Optional<Subreddit> subredditOptional = subredditRepo.findByName(subredditName);
		Subreddit subreddit = subredditOptional.orElseThrow(() -> new SausocialException(String.format("Subreddit with ID: %s not found.", subredditName)));
		return subreddit;
	}
	
	@Override
	public List<SubredditDTO> getAllSubreddits() {
		return subredditRepo.findAll().stream().map(subreddit -> {return this.buildSubredditDTO(subreddit);}).collect(Collectors.toList());
	}
	
	private SubredditDTO buildSubredditDTO(Subreddit subreddit) {
		return SubredditDTO.builder()
							   .name(subreddit.getName())
							   .description(subreddit.getDescription())
							   .id(subreddit.getId())
							   .postCount(subreddit.getPosts().size())
							   .username(subreddit.getUser().getUsername())
						   .build();
	}


}
