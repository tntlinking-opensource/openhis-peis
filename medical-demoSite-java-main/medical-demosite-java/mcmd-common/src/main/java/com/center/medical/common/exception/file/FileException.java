package com.center.medical.common.exception.file;

import com.center.medical.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author 路飞
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
