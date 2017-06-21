package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dao.MysqlDao;
import domain.AlbumInfor;
import domain.PhotoInfor;
import domain.User;
import validator.LoginValidator;
import validator.SignupValidator;

@Controller
public class AlbumController {

	@Autowired
	private MysqlDao mysqlDao;

	private static final Log logger = LogFactory.getLog(AlbumController.class);
	
	private String getShowAlbumInfor(long userId, String albumName,
			Model model) {
		String sql = "select photoname, description, open, "
				+ "comment from photo where userid = ? and "
				+ "albumname = ?";
		SqlRowSet result = mysqlDao.query(sql, userId, albumName);
		LinkedList<PhotoInfor> photoInfors = new LinkedList<PhotoInfor>();
		while(result.next()) {
			photoInfors.add(new PhotoInfor(result.getString(1),
					result.getString(2), result.getBoolean(3),
					result.getString(4)));
		}
		model.addAttribute("photoInfors", photoInfors);
		model.addAttribute("albumName", albumName);
		model.addAttribute("userId", userId);
		for(PhotoInfor temp: photoInfors) {
			logger.info("图片信息:\n\t图片名称: " + temp.getPhotoName()
					+"\n\t图片描述: " + temp.getDescription()
					+"\n\t评论: " + temp.getComment());
		}
		return "ShowAlbum";
	}
	
	private String getManageAlbumInfor(long userId, String albumName,
			Model model) {
		String sql = "select photoname, description, open, "
				+ "comment from photo where userid = ? and "
				+ "albumname = ?";
		SqlRowSet result = mysqlDao.query(sql, userId, albumName);
		LinkedList<PhotoInfor> photoInfors = new LinkedList<PhotoInfor>();
		while(result.next()) {
			photoInfors.add(new PhotoInfor(result.getString(1),
					result.getString(2), result.getBoolean(3),
					result.getString(4)));
		}
		model.addAttribute("photoInfors", photoInfors);
		model.addAttribute("albumName", albumName);
		model.addAttribute("userId", userId);
		for(PhotoInfor temp: photoInfors) {
			logger.info("图片信息:\n\t图片名称: " + temp.getPhotoName()
					+"\n\t图片描述: " + temp.getDescription()
					+"\n\t评论: " + temp.getComment());
		}
		return "ManageAlbum";
	}
	
	private String getMainPageInfor(User user, Model model) {
		String sql = "select albumname, coverpath, photonum from album "
				+ "where userid = ?";
		SqlRowSet result = mysqlDao.query(sql, user.getId());
		LinkedList<AlbumInfor> albumInfors = new LinkedList<AlbumInfor>();
		while(result.next()) {
			albumInfors.add(new AlbumInfor(result.getString(1), 
					result.getString(2),
					result.getInt(3)));
		}
		model.addAttribute("albumInfors", albumInfors);
		for(AlbumInfor temp: albumInfors) {
			logger.info("相册信息:\n\t相册名称: " + temp.getAlbumName()
					+ "\n\t相片数量: " + temp.getPhotoNum());
		}
		return "MainPage";
	}
	
	@RequestMapping(value = "/login")
	public String login(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user != null && user.getId() != -1) {
			model.addAttribute("user", user);
			return getMainPageInfor(user, model);
		}
		user = new User();
		user.setId(-1);
		model.addAttribute("user", user);
		return "Login";
	}
	
	@RequestMapping(value = "/signup")
	public String signup(Model model, HttpSession session) {
		User user = new User();
		user.setId(-1);
		model.addAttribute("user", user);
		return "Signup";
	}
	
	@RequestMapping(value = "/check-login")
	public String checkLogin(@ModelAttribute User user, 
			BindingResult bindingResult,
			Model model, HttpSession session) throws SQLException {
		User temp = (User) session.getAttribute("user");
		if(temp != null && temp.getId() != -1) {
			model.addAttribute("user", temp);
			return getMainPageInfor(temp, model);
		}
		
		logger.info(user.getUsername());
		logger.info(user.getPassword());
		
		LoginValidator loginValidator = new LoginValidator();
		loginValidator.validate(user, bindingResult);
		if(bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			logger.info("Code:" + fieldError.getCode() + ", field:"
					+ fieldError.getField());
			return "Login";
		}
		
		String sql = "select * from user where username = ? and password = ?";
		SqlRowSet result = mysqlDao.query(sql, user.getUsername(),
				user.getPassword());
		if(result.next() == false) {
			logger.info("Invaild username or password, login failed.");
			model.addAttribute("error", "用户名或密码错误，请重新登录");
			return "Login";
		}
		user.setId(result.getLong(1));
		user.setEmail(result.getString(4));
		user.setFacephoto(result.getString(5));
		session.setAttribute("user", user);
		logger.info("Login successful! User's id is: " + user.getId());
		return getMainPageInfor(user, model);
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/infor") 
	public String infor(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null || user.getId() == -1) {
			model.addAttribute("error", new String("拒绝访问"));
			return "Error";
		}
		model.addAttribute("user", user);
		return "Infor";
	}
	
	@RequestMapping(value = "/change-infor")
	public String changeinfor(@ModelAttribute User user, 
			HttpSession session, Model model,
			BindingResult bindingResult) {
		User temp = (User) session.getAttribute("user");
		if(temp == null || temp.getId() == -1) {
			model.addAttribute("error", new String("拒绝访问"));
			return "Error";
		}
		
		SignupValidator signupValidator = new SignupValidator();
		signupValidator.validate(user, bindingResult);
		if(bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			logger.info("Code:" + fieldError.getCode() + ", field:"
					+ fieldError.getField());
			return "Infor";
		}
		
		if(user.getUsername().equals(temp.getUsername()) == false) {
			String sql = "select * from user where username = ?";
			SqlRowSet result = mysqlDao.query(sql, user.getUsername());
			if(result.next() == true) {
				logger.info("用户试图修改的用户名已存在，修改失败");
				model.addAttribute("error", "该用户名已存在，修改失败");
				model.addAttribute("user", user);
				return "Infor";
			}
		}
		String sql = "update user set username = ?, password = ?,"
				+ " email = ?, facephoto = ? where id = ?";
		logger.info("change username: " + user.getUsername());
		logger.info("change user's id: " + temp.getId());
		mysqlDao.update(sql, user.getUsername(), user.getPassword(),
				user.getEmail(), user.getFacephoto(), temp.getId());
		user.setId(temp.getId());
		session.setAttribute("user", user);
		return getMainPageInfor(user, model);
	}
	
	@RequestMapping(value = "/check-signup")
	public String checksignup(@ModelAttribute User user, 
			HttpSession session, Model model,
			BindingResult bindingResult) {
		SignupValidator signupValidator = new SignupValidator();
		signupValidator.validate(user, bindingResult);
		if(bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			logger.info("Code:" + fieldError.getCode() + ", field:"
					+ fieldError.getField());
			return "Signup";
		}
		
		String sql = "select * from user where username = ?";
		SqlRowSet result = mysqlDao.query(sql, user.getUsername());
		if(result.next() == true) {
			logger.info("用户名已存在，注册失败");
			model.addAttribute("error", "该用户名已存在，注册失败");
			model.addAttribute("user", user);
			return "Signup";
		}
		sql = "insert into user(username, password, email, "
				+ "facephoto) values(?, ?, ?, ?)";
		mysqlDao.update(sql, user.getUsername(), user.getPassword(),
				user.getEmail(), user.getFacephoto());
		
		sql = "select id from user where username = ?";
		result = mysqlDao.query(sql, user.getUsername());
		result.next();
		user.setId(result.getLong(1));
		logger.info("用户注册成功，id为" + user.getId());
		logger.info("竟然有人注册");
		session.setAttribute("user", user);
		return getMainPageInfor(user, model);
	}
	
	@RequestMapping(value = "/showalbum/{userId}/{albumName}")
	public String showalbum(Model model, HttpSession session,
			@PathVariable("userId") long userId,
			@PathVariable("albumName") String albumName) {
		User user = (User) session.getAttribute("user");
		if(user == null || user.getId() == -1) {
			model.addAttribute("error", new String("拒绝访问"));
			return "Error";
		}
		logger.info("The user's id is:" + userId + ", and the albumName is: " + albumName);
		if(userId == user.getId())
			return getManageAlbumInfor(userId, albumName, model);
		return getShowAlbumInfor(userId, albumName, model);
	}
	
	@RequestMapping(value = "/addpicture")
	public String addpicture(@ModelAttribute String albumName, 
			HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if(user == null || user.getId() == -1) {
			model.addAttribute("error", new String("拒绝访问"));
			return "Error";
		}
		return "AddPicture";
	}
	
	@RequestMapping(value = "/upload")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, 
    		Model model, HttpSession session, 
    		@ModelAttribute String albumName) throws IOException {
		User user = (User) session.getAttribute("user");
        System.out.println("开始");
        String path = session.getServletContext().getRealPath("/");
        path += "WebContent/img/" + user.getId();
        String fileName = file.getOriginalFilename();
        System.out.println(path);
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.createNewFile();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "MainPage";
    }
}