package ru.saubulprojects.sausocial.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {
	
	@Id
	@SequenceGenerator(name = "subscription_id_seq",sequenceName = "subscription_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "subscription_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "owner_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "owner_user_id_fk"))
	private User owner;
	
	@ManyToOne
	@JoinColumn(name = "subscriber_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "subscriber_user_id_fk"))
	private User subscriber;
	
}
