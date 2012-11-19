package com.menatwork.model;

public class JobPosition {

	private final String id;
	private final String title;
	private final boolean isCurrent;
	private final String userId;

	public JobPosition(final String id, final String title,
			final boolean isCurrent, final String userId) {
		this.id = id;
		this.title = title;
		this.isCurrent = isCurrent;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public boolean isCurrent() {
		return isCurrent;
	}

	public String getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "JobPosition [id=" + id + ", title=" + title + ", isCurrent="
				+ isCurrent + ", userId=" + userId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (isCurrent ? 1231 : 1237);
		result = prime * result + (title == null ? 0 : title.hashCode());
		result = prime * result + (userId == null ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final JobPosition other = (JobPosition) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isCurrent != other.isCurrent)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
