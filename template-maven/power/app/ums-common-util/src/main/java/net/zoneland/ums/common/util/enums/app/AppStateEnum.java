/**
 * 
 */
package net.zoneland.ums.common.util.enums.app;

/**
 * @author louguodong
 *
 */
public enum AppStateEnum {

	ENABLED("1","可用"),
	DISABLED("2","不可用");
	
	private String value;
	private String description;
	
	AppStateEnum(String value,String description){
		this.value = value;
		this.description = description;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public static String getDescriptionByValue(String value){
		if(value==null || value.equals("")){
			return "";
		}
		AppStateEnum[] states = AppStateEnum.values();
		for (int i = 0; i < states.length; i++) {
			if(value.equals(states[i].getValue())){
				return states[i].getDescription();
			}
		}
		return "";
	}
}
