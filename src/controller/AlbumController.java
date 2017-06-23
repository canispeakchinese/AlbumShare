package controller;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import dao.MysqlDao;
import domain.AlbumInfor;
import domain.Photo;
import domain.PhotoInfor;
import domain.UploadedPhoto;
import domain.User;
import validator.AddPictureValidator;
import validator.LoginValidator;
import validator.PhotoInforValidator;
import validator.SignupValidator;

@Controller
public class AlbumController {

	@Autowired
	private MysqlDao mysqlDao;

	private static final Log logger = LogFactory.getLog(AlbumController.class);
	
	private String getShowAlbumInfor(int userId, int albumId,
			Model model) {
		String sql = "select id, photoname, description, open, "
				+ "comment from photo where userid = ? and "
				+ "albumid = ?";
		SqlRowSet result = mysqlDao.query(sql, userId, albumId);
		LinkedList<PhotoInfor> photoInfors = new LinkedList<PhotoInfor>();
		while(result.next()) {
			photoInfors.add(new PhotoInfor(result.getInt(1),
					result.getString(2),
					result.getString(3), result.getBoolean(4),
					result.getString(5)));
		}
		model.addAttribute("photoInfors", photoInfors);
		for(PhotoInfor temp: photoInfors) {
			logger.info("图片信息:\n\t图片名称: " + temp.getPhotoName()
					+"\n\t图片描述: " + temp.getDescription()
					+"\n\t评论: " + temp.getComment());
		}
		return "ShowAlbum";
	}
	
	private String getManageAlbumInfor(int userId, int albumId,
			Model model) {
		String sql = "select id, photoname, description, open, "
				+ "comment from photo where userid = ? and "
				+ "albumid = ?";
		SqlRowSet result = mysqlDao.query(sql, userId, albumId);
		LinkedList<PhotoInfor> photoInfors = new LinkedList<PhotoInfor>();
		while(result.next()) {
			photoInfors.add(new PhotoInfor(result.getInt(1),
					result.getString(2), result.getString(3), 
					result.getBoolean(4), result.getString(5)));
		}
		model.addAttribute("photoInfors", photoInfors);
		for(PhotoInfor temp: photoInfors) {
			logger.info("图片信息:\n\t图片id: " + temp.getId()
					+"\n\t图片名称: " + temp.getPhotoName()
					+"\n\t图片描述: " + temp.getDescription()
					+"\n\t评论: " + temp.getComment()
					+"\n\tAlbumId: " + albumId);
		}
		return "ManageAlbum";
	}
	
	private String getMainPageInfor(User user, Model model) {
		String sql = "select id, albumname, coverpath, photonum from album "
				+ "where userid = ?";
		SqlRowSet result = mysqlDao.query(sql, user.getId());
		LinkedList<AlbumInfor> albumInfors = new LinkedList<AlbumInfor>();
		while(result.next()) {
			albumInfors.add(new AlbumInfor(result.getInt(1),
					result.getString(2), result.getString(3),
					result.getInt(4)));
		}
		model.addAttribute("albumInfors", albumInfors);
		for(AlbumInfor temp: albumInfors) {
			logger.info("相册信息:\n\t相册名称: " + temp.getAlbumName()
					+ "\n\t相片数量: " + temp.getPhotoNum());
		}
		return "MainPage";
	}
	
	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/login";
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
		user.setId(result.getInt(1));
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
			BindingResult bindingResult,
			HttpServletRequest servletRequest) {
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
		
		int userId = 1;
		sql = "select max(id) from user";
		result = mysqlDao.query(sql);
		if(result.next()) {
			userId = result.getInt(1) + 1;
		}
		System.out.println("userId: " + userId);
		
		File dir = new File(servletRequest.getServletContext()
                .getRealPath("/img") + "/" + userId + "/");
		System.out.println("dir: " + dir);
		if(dir.exists()) {
			System.out.println("创建用户文件夹出现意外，该文件夹已存在，继续操作");
		} else if(dir.mkdirs() == false) {
			System.out.println("创建用户文件夹失败，请重试");
			return "Signup";
		}
		
		sql = "insert into user values(?, ?, ?, ?, ?)";
		mysqlDao.update(sql, userId, user.getUsername(), user.getPassword(),
				user.getEmail(), user.getFacephoto());
		
		user.setId(userId);
		logger.info("用户注册成功，id为" + user.getId());
		logger.info("竟然有人注册");
		session.setAttribute("user", user);
		return getMainPageInfor(user, model);
	}
	
	@RequestMapping(value = "/showalbum/{userId}/{albumId}")
	public String showalbum(Model model, HttpSession session,
			@PathVariable("userId") int userId,
			@PathVariable("albumId") int albumId) {
		User user = (User) session.getAttribute("user");
		if(user == null || user.getId() == -1) {
			model.addAttribute("error", new String("拒绝访问"));
			return "Error";
		}
		
		String sql = "select * from album where userid = ? and id = ?";
		SqlRowSet result = mysqlDao.query(sql, userId, albumId);
		if(!result.next()) {
			model.addAttribute("error", new String("访问相册不存在"));
			return "Error";
		}
		
		session.setAttribute("userId", userId);
		session.setAttribute("albumId", albumId);
		logger.info("The user's id is:" + userId + ", and the albumId is: " + albumId);
		if(userId == user.getId())
			return getManageAlbumInfor(userId, albumId, model);
		return getShowAlbumInfor(userId, albumId, model);
	}
	
	@RequestMapping(value = "/addpicture")
	public String addpicture(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if(user == null || user.getId() != (int) session.getAttribute("userId")) {
			model.addAttribute("error", new String("拒绝访问"));
			return "Error";
		}
		model.addAttribute("uploadedPhoto", new UploadedPhoto());
		return "AddPicture";
	}
	
	@RequestMapping(value = "/upload")
    public String upload(@ModelAttribute UploadedPhoto uploadedPhoto, 
    		Model model, HttpSession session, 
    		HttpServletRequest servletRequest, 
    		BindingResult bindingResult) {
		User user = (User) session.getAttribute("user");
		int userId = (int) session.getAttribute("userId");
		if(user == null || user.getId() != userId) {
			model.addAttribute("error", new String("拒绝访问"));
			return "Error";
		}
		
		AddPictureValidator addPictureValidator = new AddPictureValidator();
		addPictureValidator.validate(uploadedPhoto, bindingResult);
		if(bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			logger.info("Code:" + fieldError.getCode() + ", field:"
					+ fieldError.getField());
			return "AddPicture";
		}
		
		int albumId = (int) session.getAttribute("albumId");
		MultipartFile file = uploadedPhoto.getFile();
        String path = servletRequest.getServletContext()
                .getRealPath("/img") + "/" + user.getId() + "/"
                + albumId + "/";
        logger.info("New Picture save at: " + path);
        
        String sql = "select max(id) from photo where userid = ?"
        		+ " and albumid = ?";
        SqlRowSet result = mysqlDao.query(sql, userId, albumId);
        int photoId = 1;
        if(result.next()) {
        	photoId = result.getInt(1) + 1;
        }
        
        File targetFile = new File(path, String.valueOf(photoId));
        
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
        	System.out.println("文件是否存在: " + targetFile.exists());
            if(targetFile.exists() == false) {
            	model.addAttribute("error", 
            			new String("存储文件出错"));
            	return "Error";
            }
            e.printStackTrace();
            model.addAttribute("error", new String("存储文件出错"));
			return "Error";
        }
        sql = "insert into photo values(?, ?, ?, ?, ?, ?, ?)";
        mysqlDao.update(sql, photoId, userId, albumId, 
        		uploadedPhoto.getDescription(),
        		true, uploadedPhoto.getPhotoName(), "");
        sql = "update album set photonum = photonum+1 where userid"
        		+ " = ? and id = ?";
        mysqlDao.update(sql, userId, albumId);
        sql = "update album set coverpath = ? where userid = ? and "
        		+ "id = ? and coverpath = ?";
        mysqlDao.update(sql, String.valueOf(photoId), 
        		userId, albumId, "../../Empty.jpg");
        return "redirect:/showalbum/" + userId + "/" + albumId;
    }
	
	@RequestMapping(value = "/delete/{photoId}")
	public String delete(Model model, HttpSession session,
			@PathVariable("photoId") int photoId,
			HttpServletRequest servletRequest) {
		int userId = (int) session.getAttribute("userId");
		User user = (User) session.getAttribute("user");
		System.out.println("photoId is: " + photoId);
		
		if(user == null || userId != user.getId()) {
			model.addAttribute("error", new String("拒绝访问"));
			return "Error";
		}
		
		int albumId = (int) session.getAttribute("albumId");
		
		File file = new File(servletRequest.getServletContext()
                .getRealPath("/img") + "/" + userId + "/"
                + albumId + "/" + photoId);
		
		if(file.delete() == false) {
			logger.info("删除图片失败");
			return "redirect:/showalbum/" + userId + "/" + albumId;
		}
		logger.info("删除图片成功");
		String sql = "delete from photo where userid = ? and albumid = ?"
				+ " and id = ?";
		mysqlDao.update(sql, userId, albumId, photoId);
		
		sql = "update album set photonum = photonum-1 where userid = ?"
				+ " and id = ?";
		mysqlDao.update(sql, userId, albumId);
		
		sql = "select coverpath from album where userid = ? and id = ?";
		SqlRowSet result = mysqlDao.query(sql, userId, albumId);
		result.next();
		if(String.valueOf(photoId).equals(result.getString(1))) {
			sql = "select id from photo where userid = ? "
					+ "and albumid = ?";
			result = mysqlDao.query(sql, userId, albumId);
			sql = "update album set coverpath = ? where userid = ? "
					+ "and id = ?";
			if(result.next()) {
				mysqlDao.update(sql, String.valueOf(result.getInt(1)), 
						userId, albumId);
			} else {
				mysqlDao.update(sql, "../../Empty.jpg", userId, albumId);
			}
		}
		
		return "redirect:/showalbum/" + userId + "/" + albumId;
	}

	@RequestMapping(value = "/addalbum")
	public String addalbum(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null || user.getId() == -1) {
			model.addAttribute("error", "拒绝访问");
			return "Error";
		}
		return "AddAlbum";
	}
	
	@RequestMapping(value = "/newalbum")
	public String newalbum(String albumName, Model model,
			HttpSession session,
			HttpServletRequest servletRequest) {
		User user = (User) session.getAttribute("user");
		if(user == null || user.getId() == -1) {
			model.addAttribute("error", "拒绝访问");
			return "Error";
		}
		
		int albumId = 1;
		String sql = "select max(id) from album";
		SqlRowSet result =mysqlDao.query(sql);
		if(result.next()) 
			albumId = result.getInt(1) + 1;
		
		File dir = new File(servletRequest.getServletContext()
                .getRealPath("/img") + "/" + user.getId() + "/"
                + albumId + "/");
		System.out.println("dir: " + dir);
		if(dir.exists()) {
			logger.info("创建相册出现意外，该文件夹已存在，仍然继续操作");
		}
		else if(dir.mkdirs() == false) {
			model.addAttribute("error", "创建相册失败，请重试");
			return "AddAlbum";
		}
		
		sql = "insert into album values(?, ?, ?, ?, ?)";
		mysqlDao.update(sql, albumId, user.getId(), albumName, 0, "../../Empty.jpg");
		return "redirect:/check-login";
	}

	@RequestMapping(value = "/deletealbum/{albumId}")
	public String deletealbum(Model model, HttpSession session,
			@PathVariable("albumId") int albumId,
			HttpServletRequest servletRequest) {
		User user = (User) session.getAttribute("user");
		int userId = (int) session.getAttribute("userId");
		if(user == null || user.getId() == -1 || userId != user.getId()) {
			model.addAttribute("user", "拒绝访问");
			return "Error";
		}
		
		File dir = new File(servletRequest.getServletContext()
                .getRealPath("/img") + "/" + user.getId() + "/"
                + albumId + "/");
		System.out.println("dir: " + dir);
		System.out.println("dir.exists: " + dir.exists());
		if(dir.exists()) {
			if (dir.isDirectory()) {
	            String[] children = dir.list();
	            for (int i=0; i<children.length; i++) {
	                File file = new File(dir, children[i]);
	                file.delete();
	            }
	            dir.delete();
	        }
		}
		System.out.println("让我们乐观的假设该文件夹成功的被删了，不然就很尴尬了");
		
		String sql = "delete from photo where userid = ? and albumid = ?";
		mysqlDao.update(sql, user.getId(), albumId);
		
		sql = "delete from album where id = ?";
		mysqlDao.update(sql, albumId);
		
		return "redirect:/check-login";
	}

	@RequestMapping(value = "/photoinfor/{photoId}")
	public String photoinfor(Model model, HttpSession session,
			@PathVariable("photoId") int photoId,
			HttpServletRequest servletRequest) {
		User user = (User) session.getAttribute("user");
		int userId = (int) session.getAttribute("userId");

		if(user == null || user.getId() == -1 || user.getId() != userId) {
			model.addAttribute("error", "拒绝访问");
			return "Error";
		}
		int albumId = (int) session.getAttribute("albumId");
		String sql = "select photoname, description, open from photo "
				+ "where userid = ? and albumid = ? and id = ?";
		SqlRowSet result = mysqlDao.query(sql, userId, albumId, photoId);
		if(!result.next()) {
			model.addAttribute("error", "查找该图片失败，请重试");
			return "Error";
		}
		session.setAttribute("photoId", photoId);
		PhotoInfor photoInfor = new PhotoInfor(photoId, result.getString(1), 
				result.getString(2),
				result.getBoolean(3), "");
		model.addAttribute("photoInfor", photoInfor);
		return "PhotoInfor";
	}

	@RequestMapping(value = "change-photo")
	public String changephoto(@ModelAttribute PhotoInfor photoInfor, 
			HttpSession session, Model model,
			BindingResult bindingResult) {
		User user = (User) session.getAttribute("user");
		int userId = (int) session.getAttribute("userId");

		if(user == null || user.getId() == -1 || user.getId() != userId) {
			model.addAttribute("error", "拒绝访问");
			return "Error";
		}
		
		PhotoInforValidator photoInforValidator = new PhotoInforValidator();
		photoInforValidator.validate(photoInfor, bindingResult);
		if(bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			logger.info("Code:" + fieldError.getCode() + ", field:"
					+ fieldError.getField());
			return "PhotoInfor";
		}
		int albumId = (int) session.getAttribute("albumId");
		int photoId = (int) session.getAttribute("photoId");
		String sql = "update photo set description = ?, open = ?, "
				+ "photoname = ? where userid = ? and albumid = ? "
				+ "and id = ?";
		mysqlDao.update(sql, photoInfor.getDescription(),
				photoInfor.getIsOpen(), photoInfor.getPhotoName(),
				userId, albumId, photoId);
		return "redirect:/showalbum/" + userId + "/" + albumId;
	}

	@RequestMapping(value = "/renamealbum/{albumId}")
	public String renamealbum(Model model, HttpSession session,
			@PathVariable("albumId") int albumId,
			HttpServletRequest servletRequest) {
		User user = (User) session.getAttribute("user");
		if(user == null || user.getId() == -1) {
			model.addAttribute("error", "拒绝访问");
			return "Error";
		}
		String sql = "select albumname from album where userid = ?"
				+ " and id = ?";
		SqlRowSet result = mysqlDao.query(sql, user.getId(), albumId);
		if(!result.next()) {
			model.addAttribute("error", "该相册不存在");
			return "Error";
		}
		session.setAttribute("albumId", albumId);
		model.addAttribute("albumName", result.getString(1));
		return "RenameAlbum";
	}
	
	@RequestMapping(value = "/changealbum")
	public String changealbum(String albumName, Model model, 
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		int albumId = (int) session.getAttribute("albumId");
		if(user == null || user.getId() == -1) {
			model.addAttribute("error", "拒绝访问");
			return "Error";
		}
		if(albumName == null)
			albumName = "";
		String sql = "update album set albumname = ? where userid = ? "
				+ "and id = ?";
		mysqlDao.update(sql, albumName, user.getId(), albumId);
		return "redirect:/check-login";
	}
	
	@RequestMapping(value = "/brower") 
	public String brower(Model model, HttpSession session) {
		LinkedList<Photo> photos = new LinkedList<Photo>();
		String sql = "select userid, albumid, id, photoname, description,"
				+ " comment from photo where open = ?";
		SqlRowSet result = mysqlDao.query(sql, true);
		while(result.next()) {
			photos.add(new Photo(result.getInt(1), result.getInt(2),
					result.getInt(3), result.getString(4),
					result.getString(5), result.getString(6)));
		}
		model.addAttribute("photos", photos);
		User user = (User) session.getAttribute("user");
		if(user != null)
			return "Visit";
		return "Brower";
	}
}