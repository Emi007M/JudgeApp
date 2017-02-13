package judgeApp.model;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author Emilia
 */
public final class Dictionary {

    private static Locale locale;
    private static String language;
    private static String country;

    private static ResourceBundle messages;

    public Dictionary() {
        language = "en";
        country = "US";
        locale = new Locale(language, country);
        locale = Locale.getDefault();

        final String dir = "judgeApp/model/dictionary/MessagesBundle";
        messages = ResourceBundle.getBundle(dir, locale);
    }

    public static String getString(String key) {
        try {  
            return messages.getString(key);
        } catch (MissingResourceException e) {  
            System.err.println(e);  
  
            return "err#";  
        }  
        
    }
    
    public static ResourceBundle getBundle(){
        return messages;
    }

}
