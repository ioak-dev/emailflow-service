package io.ioak.emailflow.space;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SpaceFilter implements Filter {

    @Autowired
    private SpaceHolder spaceHolder;

    @Value("${spring.data.mongodb.database}")
    private String defaultSpaceDB;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            String path = request.getRequestURI().substring(request.getContextPath().length());
            if (path.startsWith("/api/space")) {
                String spaceId = extractSpace(request);
                this.spaceHolder.setSpaceId(spaceId);

            } else if (path.startsWith("/api/common")){
                this.spaceHolder.setSpaceId(defaultSpaceDB);
            } else if (path.startsWith("/chat")){

            }

            chain.doFilter(servletRequest, servletResponse);
        }catch (ClientAbortException ce) {
            log.warn("********ClientAbortException during spacefilter check***");
            ce.printStackTrace();
        }catch (IOException ie) {
            log.warn("********IOException during spacefilter check***");
            ie.printStackTrace();
        }catch (Exception e) {
            log.warn("********Exception during spacefilter check***");
            e.printStackTrace();
        }
        finally {
            this.spaceHolder.clear();
        }
    }

    private String extractSpace(HttpServletRequest request) {
        Matcher matcher = matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private Matcher matcher(HttpServletRequest request) {
        Pattern pattern = Pattern.compile("/api/space/(.*?)/");
        return pattern.matcher(request.getRequestURI());
    }


}
