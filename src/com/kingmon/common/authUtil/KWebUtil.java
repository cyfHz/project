package com.kingmon.common.authUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import com.kingmon.base.util.SubApStrUtil;

public class KWebUtil {
//	private static final Logger log = LoggerFactory.getLogger(KWebUtil.class);
	//Servlet 2.3+ 
    public static final String INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";
    public static final String INCLUDE_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.include.context_path";
    public static final String INCLUDE_SERVLET_PATH_ATTRIBUTE = "javax.servlet.include.servlet_path";
    public static final String INCLUDE_PATH_INFO_ATTRIBUTE = "javax.servlet.include.path_info";
    public static final String INCLUDE_QUERY_STRING_ATTRIBUTE = "javax.servlet.include.query_string";
// Servlet 2.4+ 
    public static final String FORWARD_REQUEST_URI_ATTRIBUTE = "javax.servlet.forward.request_uri";
    public static final String FORWARD_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.forward.context_path";
    public static final String FORWARD_SERVLET_PATH_ATTRIBUTE = "javax.servlet.forward.servlet_path";
    public static final String FORWARD_PATH_INFO_ATTRIBUTE = "javax.servlet.forward.path_info";
    public static final String FORWARD_QUERY_STRING_ATTRIBUTE = "javax.servlet.forward.query_string";
    
    public static final String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";
    
    private static PathMatcher  pathMatcher = new AntPathMatcher();
    
    public static boolean pathsMatch(String pattern, String path) {
        return pathMatcher.match(pattern, path);
    }
    
    public static boolean pathsMatch(String path, ServletRequest request) {
		String requestURI = getPathWithinApplication(request);
		return pathsMatch(path, requestURI);
	}

    public static String getPathWithinApplication(ServletRequest request) {
		return getPathWithinApplication(toHttp(request));
	}
	   
	public static HttpServletRequest toHttp(ServletRequest request) {
		return (HttpServletRequest) request;
	}

	public static HttpServletResponse toHttp(ServletResponse response) {
		return (HttpServletResponse) response;
	}

	public static String getPathWithinApplication(HttpServletRequest request) {
		String contextPath = getContextPath(request);
		String requestUri = getRequestUri(request);
		if (SubApStrUtil.startsWithIgnoreCase(requestUri, contextPath)) {
			String path = requestUri.substring(contextPath.length());
			return (StringUtils.hasText(path) ? path : "/");
		} else {
			return requestUri;
		}
	}
	   
	   public static String getContextPath(HttpServletRequest request) {
	        String contextPath = (String) request.getAttribute(INCLUDE_CONTEXT_PATH_ATTRIBUTE);
	        if (contextPath == null) {
	            contextPath = request.getContextPath();
	        }
	        if ("/".equals(contextPath)) {
	            contextPath = "";
	        }
	        return decodeRequestString(request, contextPath);
	    }
	   public static String getRequestUri(HttpServletRequest request) {
	        String uri = (String) request.getAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE);
	        if (uri == null) {
	            uri = request.getRequestURI();
	        }
	        return decodeAndCleanUriString(request, uri);
	    }
	   
	   private static String decodeAndCleanUriString(HttpServletRequest request, String uri) {
	        uri = decodeRequestString(request, uri);
	        int semicolonIndex = uri.indexOf(';');
	        return (semicolonIndex != -1 ? uri.substring(0, semicolonIndex) : uri);
	    }
	   
	    @SuppressWarnings("deprecation")
		public static String decodeRequestString(HttpServletRequest request, String source) {
	        String enc = determineEncoding(request);
	        try {
	            return URLDecoder.decode(source, enc);
	        } catch (UnsupportedEncodingException ex) {
	            return URLDecoder.decode(source);
	        }
	    }
	   
	    protected static String determineEncoding(HttpServletRequest request) {
	        String enc = request.getCharacterEncoding();
	        if (enc == null) {
	            enc = DEFAULT_CHARACTER_ENCODING;
	        }
	        return enc;
	    }
}
