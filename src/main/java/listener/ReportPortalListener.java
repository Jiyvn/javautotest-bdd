//package listener;
//
//import com.epam.reportportal.cucumber.ScenarioReporter;
//import com.epam.reportportal.message.ReportPortalMessage;
//import com.epam.reportportal.service.ReportPortal;
//import com.epam.reportportal.service.item.TestCaseIdEntry;
//import com.epam.reportportal.utils.MimeTypeDetector;
//import com.epam.reportportal.utils.TestCaseIdUtils;
//import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
//import com.google.common.io.ByteSource;
//import io.cucumber.plugin.event.TestCase;
//import okhttp3.MediaType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.io.IOException;
//import java.net.URI;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Optional;
//
//
//public class ReportPortalListener extends ScenarioReporter {
//   private static final Logger LOGGER = LoggerFactory.getLogger(ReportPortalListener.class);
//
//   // Set retry = true for grouping the retried test item
//   @Nonnull
//   @Override
//   protected StartTestItemRQ buildStartScenarioRequest(@Nonnull TestCase testCase, @Nonnull String name, @Nonnull URI uri, int line) {
//       StartTestItemRQ rq = new StartTestItemRQ();
//       rq.setName(name);
//       String description = this.getDescription(testCase, uri);
//       String codeRef = this.getCodeRef(uri, line);
//       rq.setRetry(true);  //added this line for retry function
//       rq.setDescription(description);
//       rq.setCodeRef(codeRef);
//       rq.setAttributes(this.extractAttributes(testCase.getTags()));
//       rq.setStartTime(Calendar.getInstance().getTime());
//       String type = this.getScenarioTestItemType();
//       rq.setType(type);
//       if ("STEP".equals(type)) {
//           rq.setTestCaseId((String)Optional.ofNullable(TestCaseIdUtils.getTestCaseId((String)codeRef, (List)null)).map(TestCaseIdEntry::getId).orElse((String)null));
//       }
//
//       return rq;
//   }
//
//
//   // Change the log level of embed image / video from default "unknown" level to "debug" level.
//   // For unknown level, it is classified as Error level in UI, difficult when do triage of the error logs
//   @Override
//   public void embedding(@Nullable String name, @Nullable String mimeType, @Nonnull byte[] data) {
//       String type = getMimeType(name, mimeType, data);
//       String attachmentName = getAttachmentName(type, name);
//       ReportPortal.emitLog(new ReportPortalMessage(ByteSource.wrap(data), type, attachmentName), "Debug", Calendar.getInstance().getTime());
//   }
//
//   @Nullable
//   private static String getDataType(@Nonnull byte[] data, @Nullable String name) {
//       try {
//           return MimeTypeDetector.detect(ByteSource.wrap(data), name);
//       } catch (IOException var3) {
//           LOGGER.warn("Unable to detect MIME type", var3);
//           return null;
//       }
//   }
//
//   public static String getMimeType(@Nullable String name, @Nullable String mimeType, @Nonnull byte[] data){
//       String type = (String)Optional.ofNullable(mimeType).filter((m) -> {
//           try {
//               MediaType.get(m);
//               return true;
//           } catch (IllegalArgumentException var2) {
//               LOGGER.warn("Incorrect media type '{}'", m);
//               return false;
//           }
//       }).orElseGet(() -> {
//           return getDataType(data, name);
//       });
//       return type;
//   }
//
//   public static String getAttachmentName(String type, String name){
//       String attachmentName = (String)Optional.ofNullable(name).filter((m) -> {
//           return !m.isEmpty();
//       }).orElseGet(() -> {
//           return (String)Optional.ofNullable(type).map((t) -> {
//               return t.substring(0, t.indexOf("/"));
//           }).orElse("");
//       });
//       return attachmentName;
//   }
//
//   public static void attach(String mimeType, String attachmentName, byte[] data, String level){
//       String type = getMimeType(null, mimeType, data);
//       ReportPortal.emitLog(new ReportPortalMessage(ByteSource.wrap(data), type, attachmentName), level, Calendar.getInstance().getTime());
//   }
//
//}
