package controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import components.Hello;

@Controller
@RequestMapping(path="/hello")
public class HelloController {

	@Autowired
	Hello hello;
	
	@GetMapping()
	public @ResponseBody String getHello() 
	{
	return hello.HelloWorld();
	}

}
