/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author SriHarsha S
 */
public class ValuePattern {
    private ValueCount count;
    private ValueType[] alltypes;
    private String SplPattern;
    private ValueType[][] splListTypes;
    private String[] SplPatternList;
    
    public ValuePattern(ValueCount c, ValueType[] vt){
        this.count = c;
        this.alltypes = vt;
    }
    
    public ValuePattern(ValueCount c, ValueType[] vt,String pt){
        this.count = c;
        this.alltypes = vt;
        this.SplPattern = pt;
    }
    
    public ValuePattern(ValueType[][] vts,String[] pt){
        this.count = ValueCount.VALUE_LIST;
        this.splListTypes = vts;
        this.SplPatternList = pt;
    }
    
    private final String NUMBER0255 = "[0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5]",
                        NUMBER0100 = "[0-9]|[1-9][0-9]|100",
                        NUMBER0360 = "[0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-9][0-9]|3[0-5][0-9]|360",
                        NUMBERFLOAT = "[0-9]*[.][0-9]+";
        
        private final String  NUMBER_LENGTH_PATTERN ="^\\d+(in|pt|pc|cm|mm|m|ft|px|em|ex)$",
                        NUMBER_ANGLE_PATTERN ="^"+NUMBER0360+"(deg|grad|rad|turn)$",
                        NUMBER_RATIO_PATTERN ="^(\\d*)[.]?(\\d+)%$",
                        STRING_PATTERN ="'?((?:\\w+-)+\\w+|\\w+)'?|\"?((?:\\w+-)+\\w+|\\w+)\"?",
                        MULTI_STRING = "("+STRING_PATTERN+")+(\\s*,\\s*"+STRING_PATTERN+")*",
                        STRING_LIST_PATTERN = "("+STRING_PATTERN+")+(\\s*"+STRING_PATTERN+")*",
                        FONT_PRED = "sans-serif|serif|monospace|cursive|fantasy",
                        FONT_LIST = MULTI_STRING+"\\s*,\\s*("+FONT_PRED+")",
                        COLOR_FULL_PATTERN ="^#[0-9A-Fa-f]{6}$",
                        COLOR_SHORT_PATTERN ="^#[0-9A-Fa-f]{3}$",
                        COLOR_RGB_PATTERN ="^rgb[(]("+NUMBER0255+"),("+NUMBER0255+"),("+NUMBER0255+")[)]$",
                        COLOR_RGBA_PATTERN ="^rgba[(]("+NUMBER0255+"),("+NUMBER0255+"),("+NUMBER0255+"),("+NUMBERFLOAT+")[)]$",
                        COLOR_NAME_PATTERN ="/aliceblue|antiquewhite|aqua|aquamarine|azure|beige|bisque|black|blanchedalmond|blue|blueviolet|brown|burlywood|cadetblue|chartreuse|chocolate|coral|cornflowerblue|cornsilk|crimson|cyan|darkblue|darkcyan|darkgoldenrod|darkgray|darkgreen|darkkhaki|darkmagenta|darkolivegreen|darkorange|darkorchid|darkred|darksalmon|darkseagreen|darkslateblue|darkslategray|darkturquoise|darkviolet|deeppink|deepskyblue|dimgray|dodgerblue|firebrick|floralwhite|forestgreen|fuchsia|gainsboro|ghostwhite|gold|goldenrod|grey|gray|green|greenyellow|honeydew|hotpink|indianred|indigo|ivory|khaki|lavender|lavenderblush|lawngreen|lemonchiffon|lightblue|lightcoral|lightcyan|lightgoldenrodyellow|lightgreen|lightgrey|lightpink|lightsalmon|lightseagreen|lightskyblue|lightslategray|lightsteelblue|lightyellow|lime|limegreen|linen|magenta|maroon|mediumauqamarine|mediumblue|mediumorchid|mediumpurple|mediumseagreen|mediumslateblue|mediumspringgreen|mediumturquoise|mediumvioletred|midnightblue|mintcream|mistyrose|moccasin|navajowhite|navy|oldlace|olive|olivedrab|orange|orangered|orchid|palegoldenrod|palegreen|paleturquoise|palevioletred|papayawhip|peachpuff|peru|pink|plum|powderblue|purple|red|rosybrown|royalblue|saddlebrown|salmon|sandybrown|seagreen|seashell|sienna|silver|skyblue|slateblue|slategray|snow|springgreen|steelblue|tan|teal|thistle|tomato|turquoise|violet|wheat|white|whitesmoke|yellow|yellowgreen/i",
                        COLOR_HSL_PATTERN ="^hsl[(]("+NUMBER0360+"),("+NUMBER0100+")%,("+NUMBER0100+")%[)]$",
                        COLOR_HSLA_PATTERN ="^hsl[(]("+NUMBER0360+"),("+NUMBER0100+")%,("+NUMBER0100+")%,("+NUMBERFLOAT+")%[)]$",
                        URL_PATTERN ="^url[(](\"[^\"]*\"|\'[^\']*\')[)]$",
                        NL = "\\d+(in|pt|pc|cm|mm|m|ft|px|em|ex)",
                        CLIP_PATTERN ="^clip[(]("+NL+"){4}[)]$";
        
    public ValueCount getValueCount(){
        return count;
    }
    
    public String getListPattern(){
        String ptrn = "";
        for(int i=0;i<splListTypes.length;i++){
            ValueType[] vtx = splListTypes[i];
            for(int j=0;j<vtx.length;j++){
                switch(vtx[j]){
                    case COLOR: ptrn += COLOR_FULL_PATTERN+"|"+COLOR_SHORT_PATTERN+"|"+COLOR_RGB_PATTERN
                                +"|"+COLOR_RGBA_PATTERN+"|"+COLOR_NAME_PATTERN+"|"+COLOR_HSL_PATTERN
                                +"|"+COLOR_HSLA_PATTERN+"|";
                            break;
                case NUMERIC : ptrn += NUMBERFLOAT
                                +"|"+NUMBER_LENGTH_PATTERN
                                +"|"+NUMBER_ANGLE_PATTERN
                                +"|"+NUMBER_RATIO_PATTERN+"|";
                                break;
                case NUM_INT : ptrn += "(\\d+)|"; break;
                case NUM_FLOAT : ptrn += NUMBERFLOAT+"|"; break;
                case NUM_PERCENT : ptrn += NUMBER_RATIO_PATTERN+"|"; break;
                case NUM_LENGTH : ptrn += NUMBER_LENGTH_PATTERN+"|"; break;
                case NUM_ANGLE : ptrn += NUMBER_ANGLE_PATTERN+"|"; break;
                case STRING : ptrn += STRING_PATTERN+"|";
                                break;
                case URL : ptrn += URL_PATTERN+"|";
                            break;
                case CLIP :ptrn += CLIP_PATTERN+"|";
                            break;
                case PREDEFINED : for(int k=0;k<SplPatternList.length;k++) 
                                    ptrn += SplPatternList[k]+"|";
                            break;
                case COMMA_STRING : ptrn += MULTI_STRING +"|";
                            break;
                case FONT_STRING : ptrn += FONT_LIST +"|";
                            break;
                case STRING_LIST : ptrn += STRING_LIST_PATTERN +"|";
                            break;
                case UNDEFINED : ptrn += "|";
                            break;
                }
            }
            ptrn = ptrn.substring(0,ptrn.length() -1)+" ";
        }
        ptrn = ptrn.substring(0,ptrn.length() -1);
        return ptrn;
    }
    
    public List<String> getPatterns(){
        List<String> allPts = new ArrayList<>();
        for(ValueType v:alltypes){
            switch(v){
                case COLOR: allPts.add(COLOR_FULL_PATTERN);
                            allPts.add(COLOR_SHORT_PATTERN);
                            allPts.add(COLOR_RGB_PATTERN);
                            allPts.add(COLOR_RGBA_PATTERN);
                            allPts.add(COLOR_NAME_PATTERN);
                            allPts.add(COLOR_HSL_PATTERN);
                            allPts.add(COLOR_HSLA_PATTERN);
                            break;
                case NUMERIC : allPts.add(NUMBERFLOAT);
                                allPts.add(NUMBER_LENGTH_PATTERN);
                                allPts.add(NUMBER_ANGLE_PATTERN);
                                allPts.add(NUMBER_RATIO_PATTERN);
                                break;
                case NUM_INT : allPts.add("(\\d+)"); break;
                case NUM_FLOAT : allPts.add(NUMBERFLOAT); break;
                case NUM_PERCENT : allPts.add(NUMBER_RATIO_PATTERN); break;
                case NUM_LENGTH : allPts.add(NUMBER_LENGTH_PATTERN); break;
                case NUM_ANGLE : allPts.add(NUMBER_ANGLE_PATTERN); break;
                case STRING : allPts.add(STRING_PATTERN);
                                break;
                case URL : allPts.add(URL_PATTERN);
                            break;
                case CLIP : allPts.add(CLIP_PATTERN);
                            break;
                case PREDEFINED : allPts.add(SplPattern);
                            break;
                case COMMA_STRING : allPts.add(MULTI_STRING);
                            break;
                case FONT_STRING : allPts.add(FONT_LIST);
                            break;
                case STRING_LIST : allPts.add(MULTI_STRING);
                            break;
                case UNDEFINED : allPts.add("");
                            break;
            }
        }
        return allPts;
    }
}
