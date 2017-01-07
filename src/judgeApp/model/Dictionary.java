/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package judgeApp.model;

import java.util.Locale;
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
    
    public Dictionary(){
        language = "en";
        country = "US";
        locale = new Locale(language, country);
        //locale = Locale.getDefault();
        
        final String dir = "judgeApp/model/dictionary/MessagesBundle";
        messages = ResourceBundle.getBundle(dir, locale);
    }
    
    public static String getString(String key){
        return messages.getString(key);
    }
    
}
