package serializable;

import java.io.Serializable;

public class StudentA implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = -4683749499945464853L;
		int id;
		String name;
		String alias;

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public StudentA() {
		}
		
		
	}