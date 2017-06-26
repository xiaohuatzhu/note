package cn.tedu.note.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tedu.note.dao.ShareDao;
import cn.tedu.note.service.ShareService;

@Service("shareService")
public class ShareServiceImpl implements ShareService {
	private static final long serialVersionUID = 8721451590745473340L;

	@Resource
	private ShareDao shareDao;

}
