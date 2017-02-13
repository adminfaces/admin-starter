
package com.github.adminfaces.starter.infra.util;


import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

/**
 * Created by rafael-pestano on 10/06/2014.
 * utility class for assertions
 */
public class Assert implements Serializable {


    public static String getMessage(String msg) {
        try {
            //return bundle.getString(msg);
            return msg;
        } catch (MissingResourceException e) {
            return msg;
        }

    }

    public static String getMessage(String key, Object... params) {
        return MessageFormat.format(getMessage(key), params);
    }

    /**
     * Assert that given expression evaluates to <code>true</code>
     * <pre class="code">Assert.isTrue(true,"message if expression evaluates to false");</pre>
     *
     * @param expression the expression
     * @param message  the exception message to use if the assertion fails
     * @throws RuntimeException if the given expression is <code>false</code>
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            if (!hasText(message)) {
                message = "assert.isTrue";
            }
            throw new RuntimeException(getMessage(message));
        }
    }

    /**
     * Assert that given expression evaluates to <code>false</code>
     * <pre class="code">Assert.notTrue(false,"message if expression evaluates to true");</pre>
     *
     * @param expression boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws RuntimeException if the given expression is <code>true</code>
     */
    public static void notTrue(boolean expression, String message) {
        if (expression) {
            if (!hasText(message)) {
                message = "assert.notTrue";
            }
            throw new RuntimeException(getMessage(message));
        }
    }

    /**
     * Assert that given objects are equal
     * <pre class="code">Assert.equals(obj1,obj2,"message if objects are NOT equal");</pre>
     *
     * @param obj1 lhs
     * @param obj2 rhs
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the given objects are not equal
     */
    public static <T extends Object> void equals(T obj1, T obj2, String message) {
        if (!obj1.equals(obj2)) {
            if (!hasText(message)) {
                message = "assert.equals";
            }
            throw new RuntimeException(getMessage(message));
        }
    }

    /**
     * Assert that given objects are NOT equal
     * <pre class="code">Assert.equals(obj1,obj2,"message if objects ARE equal");</pre>
     *
     * @param obj1 lhs
     * @param obj2 rhs
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the given objects ARE equal
     */
    public static <T extends Object> void notEquals(T obj1, T obj2, String message) {
        if (obj1.equals(obj2)) {
            if (!hasText(message)) {
                message = "assert.notEquals";
            }
            throw new RuntimeException(getMessage(message));
        }
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Assert that given object is null
     * <pre class="code">Assert.equals(obj1,obj2,"message if object is NOT null");</pre>
     *
     * @param object the object
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the given object is NOT null
     */
    public static void isNull(Object object, String message) {
        if (!isNull(object)) {
            if (message == null) {
                message = "assert.isNull";
            }
            throw new RuntimeException(getMessage(message));
        }
    }

    public static boolean notNull(Object object) {
        return object != null;
    }

    /**
     * Assert that given object is NOT null
     * <pre class="code">Assert.equals(obj1,obj2,"message if object IS null");</pre>
     *
     * @param object the object
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the given object IS null
     */
    public static void notNull(Object object, String message) {
        if (isNull(object)) {
            if (!hasText(message)) {
                message = "assert.notNull";
            }
            throw new RuntimeException(getMessage(message));
        }
    }


    public static boolean hasLength(String text) {
        return text != null && text.length() > 0;
    }

    /**
     * Assert that the given text has any character, blank included.
     * <pre class="code">Assert.hasLength(name);</pre>
     *
     * @param text the text
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the given text has any character or has only blanks.
     */
    public static void hasLength(String text, String message) {
        if (!hasLength(text)) {
            if (!hasText(message)) {
                message = "assert.hasLength";
            }
            throw new RuntimeException(getMessage(message));
        }
    }


    /**
     * Assert that the given text has any character.
     * <pre class="code">Assert.hasText(name);</pre>
     *
     * @param text the text
     */
    public static boolean hasText(String text) {
        if (!hasLength(text)) {
            return false;
        }
        int strLen = text.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return true;//has text
            }
        }
        return false;
    }

    /**
     * Assert that the given text has any character.
     * <pre class="code">Assert.hasText(name,"message if the given text has no characters");</pre>
     *
     * @param text the text
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the given text has no characters.
     */
    public static void hasText(String text, String message) {
        if (!hasText(text)) {
            if (!hasText(message)) {
                message = "assert.hasText";
            }
            throw new RuntimeException(getMessage(message));
        }
    }


    /**
     * Assert that the given text contain the given substring.
     * <pre class="code">Assert.contains("i am rod", "rod","message if textToSearch does NOT contains substring");</pre>
     *
     * @param textToSearch the text to search
     * @param substring    the substring to find within the text
     * @param message      the exception message to use if the assertion fails
     * @throws RuntimeException if textToSearch does NOT contains substring
     */
    public static void contains(String textToSearch, String substring, String message) {
        if (!contains(textToSearch, substring)) {
            if (!hasText(message)) {
                message = "assert.contains";
            }
            throw new RuntimeException(getMessage(message));
        }
    }

    public static boolean contains(String textToSearch, String substring) {
        if (hasText(textToSearch) && hasText(substring) && textToSearch.contains(substring)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <pre class="code">Assert.notContains(name, "message if textToSearch contains substring");</pre>
     *
     * @param textToSearch the text to search
     * @param substring    the substring to find within the text
     * @param message      the exception message to use if the assertion fails
     * @throws RuntimeException if textToSearch contains substring
     */
    public static void notContains(String textToSearch, String substring, String message) {
        if (contains(textToSearch, substring)) {
            if (!hasText(message)) {
                message = "assert.notContains";
            }
            throw new RuntimeException(getMessage(message));
        }
    }


    /**
     * Assert that an array has elements; that is, it must not be
     * {@code null} and must have at least one element.
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(Object[] array, String message) {
        if (!notEmpty(array)) {
            if (!hasText(message)) {
                message = "assert.notEmpty";
            }
            throw new RuntimeException(getMessage(message));
        }
    }

    /**
     * Assert that an array has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="code">Assert.notEmpty(array);</pre>
     *
     * @param array the array to check
     * @throws RuntimeException if the object array is {@code null} or has no elements
     */
    public static boolean notEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        if (!hasElements(array)) {
            return false;
        }
        return true;
    }

    /**
     * Assert that an array has no null elements.
     * Note: Does not complain if the array is empty!
     * <pre class="code">Assert.noNullElements(array, "The array must have non-null elements");</pre>
     *
     * @param array the array to check
     */
    public static boolean notNull(Object[] array) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param array the array to check
     * @return {@code true} if array has at least one not null element
     */
    public static boolean hasElements(Object[] array) {
        if (isNull(array) || array.length == 0) {
            return false;
        }
        for (Object element : array) {
            if (element != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param list the list to check
     * @return {@code true} if list has at least one not null element
     */
    public static boolean hasElements(List<?> list) {
        if (isNull(list) || list.isEmpty()) {
            return false;
        }
        for (Object element : list) {
            if (element != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Assert that an array has no null elements.
     * Note: Does not complain if the array is empty!
     * <pre class="code">Assert.noNullElements(array);</pre>
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the object array contains a {@code null} element
     */
    public static void notNull(Object[] array, String message) {
        if (!notNull(array)) {
            if (!hasText(message)) {
                message = "assert.notNull";
            }
            throw new RuntimeException(getMessage(message));
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
     *
     * @param collection the collection to check
     * @param message    the exception message to use if the assertion fails
     * @throws RuntimeException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            if (!hasText(message)) {
                message = "assert.notEmpty";
            }
            throw new RuntimeException(getMessage(message));
        }
    }


    /**
     * Assert that a Map has entries; that is, it must not be {@code null}
     * and must have at least one entry.
     * <pre class="code">Assert.notEmpty(map, "Map must have entries");</pre>
     *
     * @param map     the map to check
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (!notEmpty(map.entrySet().toArray())) {
            if (!hasText(message)) {
                message = "assert.notEmpty";
            }
            throw new RuntimeException(getMessage(message));
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null}
     * and must have at least one entry.
     * <pre class="code">Assert.notEmpty(map);</pre>
     *
     * @param map the map to check
     */
    public static boolean notEmpty(Map<?, ?> map) {
        if (isNull(map)) {
            return false;
        }
        if (!hasElements(map.entrySet().toArray())) {
            return false;
        }
        return true;
    }

    //TODO asserts with dates


}
