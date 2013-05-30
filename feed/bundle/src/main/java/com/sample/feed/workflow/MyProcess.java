package com.sample.feed.workflow;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.log4j.Logger;
import org.osgi.framework.Constants;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

@Component
@Service
public class MyProcess implements WorkflowProcess {
 
	private Logger log = Logger.getLogger(MyProcess.class);
	
    @Property(value = "An example workflow process implementation.")
    static final String DESCRIPTION = Constants.SERVICE_DESCRIPTION; 
    @Property(value = "Adobe")
    static final String VENDOR = Constants.SERVICE_VENDOR;
    @Property(value = "My Sample Workflow Process")
    static final String LABEL="process.label";
  
    private static final String TYPE_JCR_PATH = "JCR_PATH";
 
    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
        WorkflowData workflowData = item.getWorkflowData();
        log.info("Executing MyProcess step");
        log.info("current Assigne:: "+item.getCurrentAssignee());
        log.info("META DATA MAP:: "+item.getMetaDataMap().toString());
        log.info("PAYLOAD TYPE"+item.getWorkflowData().getPayloadType());
        log.info("ROUTES :: "+session.getRoutes(item));
        if (workflowData.getPayloadType().equals(TYPE_JCR_PATH)) {
            String path = workflowData.getPayload().toString() + "/jcr:content";
            try {
                Session jcrSession = session.getSession(); 
                Node node = (Node) jcrSession.getItem(path);
                if (node != null) {
                    node.setProperty("approved", readArgument(args));
                    jcrSession.save();
                    log.info("APPROVED");
                }
            } catch (RepositoryException e) {
                throw new WorkflowException(e.getMessage(), e);
            }
        }
    }
 
    private boolean readArgument(MetaDataMap args) {
        String argument = args.get("PROCESS_ARGS", "false");
        return argument.equalsIgnoreCase("true");
    }
}