package Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a CSS rule.
 */

public final class Rule {

	private List<Selector> selectors;
	private List<PropertyValue> propertyValues;

	/**
	 * Creates a rule with a single selector.
	 * 
	 * @param selector A selector that the rule should initial with.
	 */

	public Rule(final Selector selector) {
		this();
		this.selectors.add(selector);
	}

	/**
	 * Creates an empty rule.
	 */

	public Rule() {
		this(new ArrayList<Selector>());
	}

	/**
	 * Creates a new rule based on a list of selectors.
	 * 
	 * @param selectors A list of selectors that the rule should initial with.
	 */

	public Rule(final List<Selector> selectors) {
		this.selectors = selectors;
		this.propertyValues = new ArrayList<PropertyValue>();
	}

	@Override
	public String toString() {

		StringBuilder out = new StringBuilder();
		out.append(implode(selectors) + " {\n");
		for (PropertyValue propertyValue : propertyValues) {
			out.append("\t" + propertyValue + ";\n");
		}
		out.append("}\n");

		return out.toString();
	}

	/**
	 * Adds a property value to the rule.
	 * 
	 * @param propertyValue The property value that should be attached.
	 */

	public void addPropertyValue(final PropertyValue propertyValue) {
		propertyValues.add(propertyValue);
	}

	/**
	 * Returns a list of all property values attached to the rule.
	 * 
	 * @return A list of all property values attached to the rule.
	 */

	public List<PropertyValue> getPropertyValues() {
		return propertyValues;
	}

	/**
	 * Returns a list of all selectors attached to the rule.
	 * 
	 * @return A list of all selectors attached to the rule.
	 */

	public List<Selector> getSelectors() {
		return selectors;
	}

	/**
	 * Adds a list of selectors to the existing list of selectors.
	 * 
	 * @param selectors A list of selectors that should be appended.
	 */

	public void addSelectors(final List<Selector> selectors) {
		this.selectors.addAll(selectors);
	}

	/**
	 * Implodes the list of selectors into a pretty String.
	 * 
	 * @param values A list of selectors.
	 * @return A fancy String.
	 */

	private String implode(final List<Selector> values) {

		StringBuilder sb = new StringBuilder();

		Iterator<Selector> iterator = values.iterator();

		while (iterator.hasNext()) {

			Selector selector = iterator.next();

			sb.append(selector.toString());

			if (iterator.hasNext()) {
				sb.append(", ");
			}

		}

		return sb.toString();

	}

	/**
	 * Removes a property value from the rule.
	 * 
	 * @param propertyValue The property value that should be removed.
	 */

	public void removePropertyValue(final PropertyValue propertyValue) {
		propertyValues.remove(propertyValue);
	}

	/**
	 * Adds a selector to the rule.
	 * 
	 * @param selector The selector that should be attached to the rule.
	 */

	public void addSelector(final Selector selector) {
		selectors.add(selector);
	}

	/**
	 * Removes a selector from the rule.
	 * 
	 * @param selector The selector that should be removed from the rule.
	 */

	public void removeSelector(final Selector selector) {
		selectors.remove(selector);
	}

}
