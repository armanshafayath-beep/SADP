import java.text.SimpleDateFormat;
import java.util.Date;

// =====================================================================
//  ADAPTER PATTERN — Adapts legacy date string formats into
//  java.util.Date objects so the rest of the system uses a
//  single Date type regardless of input format.
// =====================================================================
public class LegacyDateAdapter {

    public static Date stringToDate(String dateStr) throws Exception {
        String[] patterns = { "yyyy-MM-dd", "dd-MM-yyyy", "MM/dd/yyyy", "dd/MM/yyyy" };
        for (String p : patterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(p);
                sdf.setLenient(false);
                return sdf.parse(dateStr);
            } catch (Exception ignored) {}
        }
        throw new Exception("Invalid date format. Supported: yyyy-MM-dd, dd-MM-yyyy, MM/dd/yyyy, dd/MM/yyyy");
    }
}
