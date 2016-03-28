package com;





public class File {
		private Integer ID;
		private String info;
		private Integer status;
		
		public File(Integer iD, String info, Integer status) {
			
			ID = iD;
			this.info = info;
			this.status = status;
		}
		public Integer getID() {
			return ID;
		}
		public void setID(Integer iD) {
			ID = iD;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((ID == null) ? 0 : ID.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			File other = (File) obj;
			if (ID == null) {
				if (other.ID != null)
					return false;
			} else if (!ID.equals(other.ID))
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			return "File [ID= " + ID + " ]";
		}
		
	}