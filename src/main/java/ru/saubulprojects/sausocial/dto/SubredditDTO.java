package ru.saubulprojects.sausocial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.saubulprojects.sausocial.entity.Subreddit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubredditDTO {

	private Long id;
	private String username;
	private String name;
	private String description;
	private int postCount;
	
	public static SubredditDTO builSubredditDTO(Subreddit subreddit) {
		SubredditDTO subredditDTO = SubredditDTO.builder()
													.name(subreddit.getName())
													.postCount(subreddit.getPosts().size())
													.username(subreddit.getUser().getUsername())
													.description(subreddit.getDescription())
												.build();
		return subredditDTO;
	}
	
}
