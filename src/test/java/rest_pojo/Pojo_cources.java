package rest_pojo;

import java.util.List;

public class Pojo_cources 
{
	private List<Pojo_cource_WebAutomation> webAutomation;
	private List<Pojo_cource_api> api;
	private List<Pojo_cource_mobile> mobile;
	
	public List<Pojo_cource_WebAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<Pojo_cource_WebAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<Pojo_cource_api> getApi() {
		return api;
	}
	public void setApi(List<Pojo_cource_api> api) {
		this.api = api;
	}
	public List<Pojo_cource_mobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<Pojo_cource_mobile> mobile) {
		this.mobile = mobile;
	}
}
