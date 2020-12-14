package io.ioak.emailflow.space;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            String[] split = path.split("/");
            this.spaceHolder.setSpaceId(defaultSpaceDB+"_"+split[2]);
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
}
