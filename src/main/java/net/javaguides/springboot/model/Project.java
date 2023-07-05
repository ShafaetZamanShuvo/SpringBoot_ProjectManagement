package net.javaguides.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 200, nullable = false)
	private String name;

	@Column(name = "intro", length = 300)
	private String intro;

	@Column(name = "status" , length = 1)
	private int status;

	@Column(name = "StartDate", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@Column(name = "EndDate", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User owner;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
	@JsonIgnore
	private List<User> members;

	public Project() {

	}

	public Project(String name, String intro, int status, LocalDate startDate, LocalDate endDate, User owner) {
		super();
		this.name = name;
		this.intro = intro;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public int calculateStatus() {
		LocalDate today = LocalDate.now();
		if (today.isBefore(startDate)) {
			return 0;
		} else if (today.isAfter(endDate)) {
			return 3;
		} else {
			return 1;
		}
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}
}
