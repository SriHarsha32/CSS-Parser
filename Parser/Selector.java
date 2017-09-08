/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */

package Parser;

/**
 * Represents a CSS selector.
 * 
 * @author <a href="mailto:christoffer@christoffer.me">Christoffer Pettersson</a>
 */

public final class Selector {

	private String name,type;
        private int line;

	/**
	 * Creates a new selector.
	 * 
	 * @param name Selector name.
	 */

	public Selector(final String name) {
            this.name = name;
            this.type = whatType();
            
	}
        
        public Selector(final String name,final int line){
            this.name = name;
            this.type = whatType();
            this.line = line;
        }
        
        private String whatType(){
            String type = "Element";
            if(name.trim().contains(" ")){
                type = "Descendent";
                String[] names = name.split(" ");
                for(String n: names){
                    char[] typ = n.toCharArray();
                    switch(typ[0]){
                        case '.': type+=", Class"; break;
                        case '#': type+=", Id"; break;
                        case '*': type+=", Universal"; break;
                    }
                    String ns = n.substring(1,n.length());
                    if(ns.contains("[")){
                        if(ns.contains("~=")) type+=", Attribute containing value";
                        else if(ns.contains("|=")) type+=", Attribute value starting with";
                        else if(ns.contains("^=")) type+=", Attribute value beginning with";
                        else if(ns.contains("$=")) type+=", Attribute value ending with";
                        else if(ns.contains("*=")) type+=", Attribute value contains substring";
                        else if(ns.contains("=")) type+=", Attribute with value";
                        else type+=", Attribute";
                    }
                    else if(ns.contains(">")) type+=", First Descendent";
                    else if(ns.contains("+")) type+=", Immediately After";
                    else if(ns.contains("~")) type+=", Preceding";
                    else if(ns.contains(":")) type+=", Pseudo";
                    else if(ns.contains(".")) type+=", Class";
                    else if(ns.contains("#")) type+=", Id";
                }
            }
            else{
                char[] typ = name.toCharArray();
                switch(typ[0]){
                    case '.': type = "Class"; break;
                    case '#': type = "Id"; break;
                    case '*': type = "Universal"; break;
                }
                String ns = name.substring(1,name.length());
                if(ns.contains("[")){
                    if(ns.contains("~=")) type+=", Attribute containing value";
                    else if(ns.contains("|=")) type+=", Attribute value starting with";
                    else if(ns.contains("^=")) type+=", Attribute value beginning with";
                    else if(ns.contains("$=")) type+=", Attribute value ending with";
                    else if(ns.contains("*=")) type+=", Attribute value contains substring";
                    else if(ns.contains("=")) type+=", Attribute with value";
                    else type+=", Attribute";
                }
                else if(ns.contains(">")) type+=", First Descendent";
                else if(ns.contains("+")) type+=", Immediately After";
                else if(ns.contains("~")) type+=", Preceding";
                else if(ns.contains(":")) type+=", Pseudo";
                else if(ns.contains(".")) type+=", Class";
                else if(ns.contains("#")) type+=", Id";
            }
            return type;
        }
        
        public String getType(){
            return type;
        }
        
        public int getLineNo(){
            return line;
        }

	@Override
	public String toString() {
            return name;
	}

	@Override
	public boolean equals(final Object object) {

		if (object instanceof Selector) {

			Selector target = (Selector) object;

			return target.name.equalsIgnoreCase(name);

		}

		return false;

	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}
