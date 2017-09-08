/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author SriHarsha S
 */
public class PropertyValueMatch {
    private final String PropertyName;
    private final ValuePattern type;
    private static Pattern ptrn;
    
    public static HashMap<String,PropertyValueMatch> listMatchers;
    
    public PropertyValueMatch(String name,ValuePattern type){
        this.PropertyName = name;
        this.type = type;
    }
    
    public static void initList(){
        listMatchers = new HashMap<>();
        // Color Properties
        AddPropertyValueMatch("color",ValueCount.ONLY_ONE,new ValueType[]{ValueType.COLOR},"");
        
        // Font Properties
        // font list
        ValueType[] ff = new ValueType[]{ValueType.FONT_STRING};
        ValueType[] fsz = new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED};
        ValueType[] fst = new ValueType[]{ValueType.PREDEFINED};
        ValueType[] fvt = new ValueType[]{ValueType.PREDEFINED};
        ValueType[] fwt = new ValueType[]{ValueType.PREDEFINED};
        String[] splList = new String[]{"","xx-large|x-large|large|medium|small|x-small|xx-small|larger|smaller","normal|italic|oblique","normal|small-caps","normal|bold|bolder|lighter|100|200|300|400|500|600|700|800|900"};
        AddPropertyValueMatch("font",new ValueType[][]{ff,fsz,fst,fvt,fwt},splList);
        
        AddPropertyValueMatch("font-family",ValueCount.ONLY_ONE,new ValueType[]{ValueType.FONT_STRING},"");
        AddPropertyValueMatch("font-size",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"xx-large|x-large|large|medium|small|x-small|xx-small|larger|smaller");
        AddPropertyValueMatch("font-style",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"normal|italic|oblique");
        AddPropertyValueMatch("font-variant",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"normal|small-caps");
        AddPropertyValueMatch("font-weight",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"normal|bold|bolder|lighter|100|200|300|400|500|600|700|800|900");
        
        //text-decoration
        AddPropertyValueMatch("text-decoration",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"none|underline|overline|line-through|blink");
        //text-transform
        AddPropertyValueMatch("text-transform",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"capitalize|uppercase|lowercase|none");
        
        // Spacing Properties
        AddPropertyValueMatch("white-space",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"normal|pre|nowrap|pre-wrap|pre-line");
        AddPropertyValueMatch("text-align",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"left|right|center|justify");
        AddPropertyValueMatch("text-indent",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"");
        AddPropertyValueMatch("line-height",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_FLOAT,ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"");
        AddPropertyValueMatch("word-spacing",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.PREDEFINED},"normal");
        AddPropertyValueMatch("letter-spacing",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.PREDEFINED},"normal");
        AddPropertyValueMatch("vertical-align",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"baseline|sub|super|top|top-text|middle|bottom|text-bottom");
        AddPropertyValueMatch("direction",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"ltr|rtl");
        AddPropertyValueMatch("unicode-bidi",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"normal|bidi-override|embed");
        
        //Layout Properties
        // margin
        AddPropertyValueMatch("margin",ValueCount.FOUR_INSTANCE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"auto");
        AddPropertyValueMatch("margin-top",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"auto");
        AddPropertyValueMatch("margin-bottom",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"auto");
        AddPropertyValueMatch("margin-left",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"auto");
        AddPropertyValueMatch("margin-right",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"auto");
        // padding
        AddPropertyValueMatch("padding",ValueCount.FOUR_INSTANCE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"");
        AddPropertyValueMatch("padding-top",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"");
        AddPropertyValueMatch("padding-bottom",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"");
        AddPropertyValueMatch("padding-left",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"");
        AddPropertyValueMatch("padding-right",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"");
        // border 
        // border list
        ValueType[] bw = new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED};
        ValueType[] bst = new ValueType[]{ValueType.PREDEFINED};
        ValueType[] bc = new ValueType[]{ValueType.COLOR,ValueType.PREDEFINED};
        String[] bsplList = new String[]{"thin|medium|thick","none|dashed|double|dotted|solid|groove|ridge|inset|outset|hidden","transparent"};
        AddPropertyValueMatch("border",new ValueType[][]{bw,bst,bc},bsplList);
        AddPropertyValueMatch("border-top",new ValueType[][]{bw,bst,bc},bsplList);
        AddPropertyValueMatch("border-bottom",new ValueType[][]{bw,bst,bc},bsplList);
        AddPropertyValueMatch("border-left",new ValueType[][]{bw,bst,bc},bsplList);
        AddPropertyValueMatch("border-right",new ValueType[][]{bw,bst,bc},bsplList);
        // border-width
        AddPropertyValueMatch("border-width",ValueCount.FOUR_INSTANCE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"thin|medium|thick");
        AddPropertyValueMatch("border-top-width",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"thin|medium|thick");
        AddPropertyValueMatch("border-bottom-width",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"thin|medium|thick");
        AddPropertyValueMatch("border-left-width",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"thin|medium|thick");
        AddPropertyValueMatch("border-right-width",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"thin|medium|thick");
        // border-style
        AddPropertyValueMatch("border-style",ValueCount.FOUR_INSTANCE,new ValueType[]{ValueType.PREDEFINED},"none|dashed|double|dotted|solid|groove|ridge|inset|outset|hidden");
        AddPropertyValueMatch("border-top-style",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"none|dashed|double|dotted|solid|groove|ridge|inset|outset|hidden");
        AddPropertyValueMatch("border-bottom-style",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"none|dashed|double|dotted|solid|groove|ridge|inset|outset|hidden");
        AddPropertyValueMatch("border-right-style",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"none|dashed|double|dotted|solid|groove|ridge|inset|outset|hidden");
        AddPropertyValueMatch("border-left-style",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"none|dashed|double|dotted|solid|groove|ridge|inset|outset|hidden");
        // border-color
        AddPropertyValueMatch("border-color",ValueCount.FOUR_INSTANCE,new ValueType[]{ValueType.COLOR,ValueType.PREDEFINED},"transparent");
        AddPropertyValueMatch("border-top-color",ValueCount.ONLY_ONE,new ValueType[]{ValueType.COLOR,ValueType.PREDEFINED},"transparent");
        AddPropertyValueMatch("border-bottom-color",ValueCount.ONLY_ONE,new ValueType[]{ValueType.COLOR,ValueType.PREDEFINED},"transparent");
        AddPropertyValueMatch("border-left-color",ValueCount.ONLY_ONE,new ValueType[]{ValueType.COLOR,ValueType.PREDEFINED},"transparent");
        AddPropertyValueMatch("border-right-color",ValueCount.ONLY_ONE,new ValueType[]{ValueType.COLOR,ValueType.PREDEFINED},"transparent");
        // width
        AddPropertyValueMatch("width",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"auto");
        AddPropertyValueMatch("min-width",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"");
        AddPropertyValueMatch("max-width",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"none");
        // height
        AddPropertyValueMatch("height",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.PREDEFINED},"auto");
        AddPropertyValueMatch("min-height",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"");
        AddPropertyValueMatch("max-height",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"none");
        // overflow
        AddPropertyValueMatch("overflow",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"visible|hidden|scroll|auto");
        // clip
        AddPropertyValueMatch("clip",ValueCount.ONLY_ONE,new ValueType[]{ValueType.CLIP,ValueType.PREDEFINED},"auto");
        
        // Positoning Properties
        // display
        AddPropertyValueMatch("display",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"none|inline|block|list-item|run-in|inline-block|inline-table|table-row-group|table-header-group|table-footer-group|table-row|table-column-group|table-column|table-cell|table-caption");
        // position
        AddPropertyValueMatch("position",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"static|relative|fixed|absolute");
        // top
        AddPropertyValueMatch("top",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"auto");
        // bottom
        AddPropertyValueMatch("bottom",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"auto");
        // right
        AddPropertyValueMatch("right",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"auto");
        // left
        AddPropertyValueMatch("left",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT},"auto");
        // float
        AddPropertyValueMatch("float",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"left|right|none");
        // clear
        AddPropertyValueMatch("clear",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"left|right|none|both");
        // z-index
        AddPropertyValueMatch("z-index",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED,ValueType.NUM_INT},"auto");
        // visibility
        AddPropertyValueMatch("visibility",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"visible|hidden|collapse");
        
        //Background Properties
        ValueType[] bga = new ValueType[]{ValueType.PREDEFINED};
        ValueType[] bgc = new ValueType[]{ValueType.COLOR,ValueType.PREDEFINED};
        ValueType[] bgi = new ValueType[]{ValueType.URL,ValueType.PREDEFINED};
        ValueType[] bgp = new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED};
        ValueType[] bgr = new ValueType[]{ValueType.PREDEFINED};
        String[] bgsplList = new String[]{"scroll|fixed","transparent","none","left|right|center|bottom|top","repeat|repeat-x|repeat-y|no-repeat"};
        AddPropertyValueMatch("background",new ValueType[][]{bga,bgc,bgi,bgp,bgr},bgsplList);
        // background-attachment
        AddPropertyValueMatch("background-attachment",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"scroll|fixed");
        // background-color
        AddPropertyValueMatch("background-color",ValueCount.ONLY_ONE,new ValueType[]{ValueType.COLOR,ValueType.PREDEFINED},"transparent");
        // background-image
        AddPropertyValueMatch("background-image",ValueCount.ONLY_ONE,new ValueType[]{ValueType.URL,ValueType.PREDEFINED},"none");
        // background-position
        AddPropertyValueMatch("background-position",ValueCount.ONE_TWO_INSTANCE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.NUM_PERCENT,ValueType.PREDEFINED},"left|right|center|bottom|top");
        // background-repeat
        AddPropertyValueMatch("background-repeat",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"repeat|repeat-x|repeat-y|no-repeat");
        
        //List-Style Properties
        ValueType[] lstt = new ValueType[]{ValueType.PREDEFINED};
        ValueType[] lstp = new ValueType[]{ValueType.PREDEFINED};
        ValueType[] lsti = new ValueType[]{ValueType.URL,ValueType.PREDEFINED};
        String[] lstsplList = new String[]{"none|disc|circle|square|decimal|decimal-leading-zero|lower-roman|upper-roman|lower-alpha|upper-alpha|lower-latin|upper-latin|lower-greek|armenian|georgian","inside|outside","none"};
        AddPropertyValueMatch("list-style",new ValueType[][]{lstt,lstp,lsti},lstsplList);
        // list-style-type
        AddPropertyValueMatch("list-style-type",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"none|disc|circle|square|decimal|decimal-leading-zero|lower-roman|upper-roman|lower-alpha|upper-alpha|lower-latin|upper-latin|lower-greek|armenian|georgian");
        // list-style-position
        AddPropertyValueMatch("list-style-position",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"inside|outside");
        // list-style-image
        AddPropertyValueMatch("list-style-image",ValueCount.ONLY_ONE,new ValueType[]{ValueType.URL,ValueType.PREDEFINED},"none");
        
        //Table Properties
        // border-collapse
        AddPropertyValueMatch("border-collapse",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"collapse|separate");
        // border-length
        AddPropertyValueMatch("border-length",ValueCount.ONE_TWO_INSTANCE,new ValueType[]{ValueType.NUM_LENGTH},"");
        // empty-cells
        AddPropertyValueMatch("empty-cells",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"show|hide");
        // table-layout
        AddPropertyValueMatch("table-layout",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"auto|fixed");
        // caption-side
        AddPropertyValueMatch("caption-side",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"top|bottom");
        // vertical-align
        AddPropertyValueMatch("vertical-align",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"baseline|top|bottom|middle");
        
        //Generated Properties
        //content
        AddPropertyValueMatch("content",ValueCount.ONLY_ONE,new ValueType[]{ValueType.UNDEFINED},"");
        //quotes
        AddPropertyValueMatch("quotes",ValueCount.ONLY_ONE,new ValueType[]{ValueType.UNDEFINED},"");
        //counter-increment
        AddPropertyValueMatch("counter-increment",ValueCount.ONLY_ONE,new ValueType[]{ValueType.UNDEFINED},"");
        //counter-reset
        AddPropertyValueMatch("counter-reset",ValueCount.ONLY_ONE,new ValueType[]{ValueType.UNDEFINED},"");
        
        //Miscallaneous Properties
        //User Interface
        // cursor
        AddPropertyValueMatch("cursor",ValueCount.ONLY_ONE,new ValueType[]{ValueType.URL,ValueType.PREDEFINED},"default|pointer|text|help|wait|progress|crosshair|move|e-resize|ne-resize|n-resize|nw-resize|w-resize|sw-resize|s-resize|se-resize");
        
        // outline
        ValueType[] otw = new ValueType[]{ValueType.NUM_LENGTH,ValueType.PREDEFINED};
        ValueType[] ots = new ValueType[]{ValueType.PREDEFINED};
        ValueType[] otc = new ValueType[]{ValueType.COLOR,ValueType.PREDEFINED};
        String[] otsplList = new String[]{"thin|medium|thick","none|dashed|double|dotted|solid|groove|ridge|inset|outset|hidden","invert"};
        AddPropertyValueMatch("list-style",new ValueType[][]{lstt,lstp,lsti},otsplList);
        // outline-width
        AddPropertyValueMatch("outline-width",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_LENGTH,ValueType.PREDEFINED},"thin|medium|thick");
        // outline-style
        AddPropertyValueMatch("outline-style",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"none|dashed|double|dotted|solid|groove|ridge|inset|outset|hidden");
        // outline-color
        AddPropertyValueMatch("outline-color",ValueCount.ONLY_ONE,new ValueType[]{ValueType.COLOR,ValueType.PREDEFINED},"invert");
        // Printing
        // page-break-before
        AddPropertyValueMatch("page-break-before",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"auto|avoid|always|left|right");
        // page-break-after
        AddPropertyValueMatch("page-break-after",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"auto|avoid|always|left|right");
        // page-break-inside
        AddPropertyValueMatch("page-break-inside",ValueCount.ONLY_ONE,new ValueType[]{ValueType.PREDEFINED},"auto|avoid");
        // widows
        AddPropertyValueMatch("widows",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_INT},"");
        // orphans
        AddPropertyValueMatch("orphans",ValueCount.ONLY_ONE,new ValueType[]{ValueType.NUM_INT},"");
    }
    
    private static void AddPropertyValueMatch(String prop,ValueCount vc,ValueType[] vts,String predef){
        PropertyValueMatch pvm = new PropertyValueMatch(prop,new ValuePattern(vc,vts,predef));
        listMatchers.put(pvm.PropertyName, pvm);
    }
    
    private static void AddPropertyValueMatch(String prop,ValueType[][] vts,String[] predef){
        PropertyValueMatch pvm = new PropertyValueMatch(prop,new ValuePattern(vts,predef));
        listMatchers.put(pvm.PropertyName, pvm);
    }
    
    private static ValueCount getValueCount(String property){
        if(listMatchers.containsKey(property))
            return listMatchers.get(property).type.getValueCount();
        else
            return ValueCount.UNDEFINED;
    }
    
    public static Boolean ValidatePV(String property,String value){
        property = property.toLowerCase();
        value = value.toLowerCase();
        ValueCount vc = getValueCount(property);
        Matcher m;
        if(vc != ValueCount.VALUE_LIST){
            List<String> allPts = listMatchers.get(property).type.getPatterns();
            switch(vc){
                case ONLY_ONE : ptrn = Pattern.compile(CreatePattern(1,allPts)); 
                                //System.out.println(ptrn.pattern());
                                m = ptrn.matcher(value);
                                return m.matches();
                case ONE_TWO_INSTANCE : ptrn = Pattern.compile(CreatePattern(1,allPts));
                                        //System.out.println(ptrn.pattern());
                                        m = ptrn.matcher(value);
                                        if(!m.matches()){
                                            ptrn = Pattern.compile(CreatePattern(2,allPts)); 
                                            //System.out.println(ptrn.pattern());
                                            m = ptrn.matcher(value);
                                            return m.matches();
                                        }
                                        return m.matches();
                case FOUR_INSTANCE : ptrn = Pattern.compile(CreatePattern(4,allPts));
                                        //System.out.println(ptrn.pattern());
                                    m = ptrn.matcher(value);
                                    return m.matches();
                case UNDEFINED : return false;
            }
        }
        else{
            String allPts = listMatchers.get(property).type.getListPattern();
            //System.out.println(ptrn.pattern());
            ptrn = Pattern.compile(allPts); 
            m = ptrn.matcher(value);
            return m.matches();
        }
        return false;
    }
    
    private static String CreatePattern(int count,List<String> pts){
        String sngl ="",mltp="";
        for(String p:pts){
            sngl += (p +"|");
        }
        sngl = sngl.substring(0,sngl.length()-1);
        for(int i=0;i<count;i++){
            mltp += sngl+" ";
        }
        mltp = mltp.substring(0,mltp.length()-1);
        
        return mltp;
    }
}
