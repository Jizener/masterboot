package com.jizan.master.api;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import com.jizan.utils.Pager;
import com.jizan.utils.JsonResult;
import com.jizan.utils.SystemConfig;
import com.jizan.master.entity.News;
import com.jizan.master.service.NewsService;

@Api(value = "资讯接口")
@RestController
@RequestMapping("/news")
public class NewsController extends BaseController{

	@Resource
	private NewsService newsService;

	@Resource
	private HttpServletRequest request;
	
	/* Show ******************/
	@ApiOperation(value = "获取news详情#v1.0",notes = "获取news详情#v1.0")
	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	@ResponseBody
	public News _show(@PathVariable("id") int id) {
		News news = this.newsService.findById(id);
		return news;
	}

	/* List ******************/
	@ApiOperation(value = "获取news列表#v1.0",notes = "获取news列表#v1.0")
	@RequestMapping(value = "/list",method=RequestMethod.POST)
	@ResponseBody
	public List<News> _list() {
		List<News> list = this.newsService.listAll();
		return list;
	}

	/* Page ******************/
	@ApiOperation(value = "根据页码获取news分页#v1.0",notes = "根据页码获取news分页#v1.0")
	@RequestMapping(value = "/page/{num:\\d+}",method=RequestMethod.GET)
	@ResponseBody
	public Pager _page(@PathVariable("num") int pageNum) {
		int limit = 20;
		Pager pager = this.newsService.pageAll(pageNum, limit);
		return pager;
	}

	/* Pager ****************/
	@ApiOperation(value = "获取news分页#v1.0",notes = "获取news分页#v1.0")
	@RequestMapping(value = "/pager",method=RequestMethod.POST)
	@ResponseBody
	public Pager _pageTable(int offset, int limit) {
		int pageNum = offset / limit + 1;
		Pager pager = this.newsService.pageAll(pageNum, limit);
		return pager;
	}

	/* Add ******************/
	@ApiOperation(value = "新增news#v1.0",notes = "新增news#v1.0")
	@RequestMapping(value = "/new",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult _new(@RequestBody News news) {
		try {
			this.newsService.add(news);
		} catch (Exception e) {
			return new JsonResult(SystemConfig.DEFEAT, SystemConfig.EXCEPTION, e);
		}
		return new JsonResult(SystemConfig.SUCCESS, SystemConfig.WIN);
	}

	/* Edit *****************/
	@ApiOperation(value = "更新news#v1.0",notes = "更新news#v1.0")
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult _update(News news) {
		try {
			this.newsService.modify(news);
		} catch (Exception e) {
			return new JsonResult(SystemConfig.DEFEAT, SystemConfig.EXCEPTION, e);
		}
		return new JsonResult(SystemConfig.SUCCESS, SystemConfig.WIN);
	}

	/* Delete ***************/
	@ApiOperation(value = "删除news#v1.0",notes = "删除news#v1.0")
	@RequestMapping(value = "/delete",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult _delete(
			@RequestParam(value = "ids", required = true, defaultValue = "") String idstring) {
		try {
			String[] ids = idstring.split(",");
			for (int i = 0; i < ids.length; i++) {
				this.newsService.removeById(Integer.parseInt(ids[i]));
			}
		} catch (Exception e) {
			return new JsonResult(SystemConfig.DEFEAT, SystemConfig.EXCEPTION, e);
		}
		return new JsonResult(SystemConfig.SUCCESS, SystemConfig.WIN);
	}

}