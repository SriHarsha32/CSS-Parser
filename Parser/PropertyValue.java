package Parser;

import Validator.PropertyValueMatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PropertyValue {

	private String property;
	private String value;
        private int line;

	/**
	 * Creates a new PropertyValue based on a property and its value.
	 * 
	 * @param property The CSS property (such as 'width' or 'color').
	 * @param value The value of the property (such as '100px' or 'red').
	 */

	public PropertyValue(final String property, final String value) {
            this.property = property;
            this.value = value;
	}

        public PropertyValue(final String property, final String value,final int line){
            this.property = property;
            this.value = value;
            this.line = line;    
        }
        
	@Override
	public String toString() {
		return property + ": " + value;
	}

	@Override
	public boolean equals(final Object object) {

		if (object instanceof PropertyValue) {

			PropertyValue target = (PropertyValue) object;

			return target.property.equalsIgnoreCase(property) && target.value.equalsIgnoreCase(value);

		}

		return false;

	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * Returns the property.
	 * 
	 * @return The property.
	 */

	public String getProperty() {
		return property;
	}

	/**
	 * Returns the value.
	 * 
	 * @return The value.
	 */

	public String getValue() {
		return value;
	}
        
        public int getLineNo(){
            return line;
        }
        
        public Boolean isValidPVPair(){
            return PropertyValueMatch.ValidatePV(property, value);
        }
}
