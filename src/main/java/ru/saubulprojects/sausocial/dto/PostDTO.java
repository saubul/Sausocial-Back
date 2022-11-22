package ru.saubulprojects.sausocial.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.saubulprojects.sausocial.entity.Post;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
	
	private Long id;
    private String postName;
    private String text;
    private String username;
    private String subredditName;
    private Integer voteCount;
    private Integer commentCount;
    private LocalDate dateCreated;
    
    public static PostDTO buildPostDTO(Post post) {
    	PostDTO postDTO = PostDTO.builder()
    								 .id(post.getId())
    								 .postName(post.getPostName())
    								 .username(post.getUser().getUsername())
    								 .dateCreated(post.getDateCreated())
    								 .text(post.getText())
    								 .subredditName(post.getSubreddit().getName())
    								 .voteCount(post.getVoteCount())
    								 .commentCount(post.getComments() != null? post.getComments().size() : 0)
    							 .build();	
    	return postDTO;
    }
    
}
