/*
 * Copyright (C) 2000-2002 JetBrains, Inc. All rights reserved.
 * Use is subject to license terms.
 */
package com.chrisbartley.idea.util;

import org.jdom.Element;

import java.util.*;

import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.util.DefaultJDOMExternalizer;

/**
 * JDOMExternalizableList: temporarily duplicated from
 * IDEA build 977's OpenAPI stubs.
 * 
 * @todo: investigate whether JDOMExternalizableList should stay or not.
 */
public class JDOMExternalizableList implements List, JDOMExternalizable {
    private static final String ATTR_LIST = "list";
    private static final String ATTR_LISTSIZE = "size";
    private static final String ATTR_ITEM = "item";
    private static final String ATTR_INDEX = "index";
    private static final String ATTR_CLASS = "class";
    private static final String ATTR_VALUE = "itemvalue";

    private ArrayList myList = new ArrayList();

    public int size() {
        return myList.size();
    }

    public boolean isEmpty() {
        return myList.isEmpty();
    }

    public boolean contains(Object o) {
        return myList.contains(o);
    }

    public Iterator iterator() {
        return myList.iterator();
    }

    public Object[] toArray() {
        return myList.toArray();
    }

    public Object[] toArray(Object a[]) {
        return myList.toArray(a);
    }

    public boolean add(Object o) {
        return myList.add(o);
    }

    public boolean remove(Object o) {
        return myList.remove(o);
    }

    public boolean containsAll(Collection c) {
        return myList.containsAll(c);
    }

    public boolean addAll(Collection c) {
        return myList.addAll(c);
    }

    public boolean addAll(int index, Collection c) {
        return myList.addAll(index, c);
    }

    public boolean removeAll(Collection c) {
        return myList.removeAll(c);
    }

    public boolean retainAll(Collection c) {
        return myList.retainAll(c);
    }

    public void clear() {
        myList.clear();
    }

    public boolean equals(Object o) {
        return myList.equals(o);
    }

    public int hashCode() {
        return myList.hashCode();
    }

    public Object get(int index) {
        return myList.get(index);
    }

    public Object set(int index, Object element) {
        return myList.set(index, element);
    }

    public void add(int index, Object element) {
        myList.add(index, element);
    }

    public Object remove(int index) {
        return myList.remove(index);
    }

    public int indexOf(Object o) {
        return myList.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return myList.lastIndexOf(o);
    }

    public ListIterator listIterator() {
        return myList.listIterator();
    }

    public ListIterator listIterator(int index) {
        return myList.listIterator(index);
    }

    public List subList(int fromIndex, int toIndex) {
        return myList.subList(fromIndex, toIndex);
    }

    public void readExternal(Element element) throws InvalidDataException {
        myList = new ArrayList();
        ArrayList resultList = null;
        for (Iterator i = element.getChildren().iterator(); i.hasNext();) {
            Element e = (Element) i.next();
            if (ATTR_LIST.equals(e.getName())) {
                Element listElement = e;
                String sizeString = listElement.getAttributeValue(ATTR_LISTSIZE);
                int listSize;
                try {
                    listSize = Integer.parseInt(sizeString);
                } catch (NumberFormatException ex) {
                    throw new InvalidDataException("Size " + sizeString + " found. Must be integer!");
                }
                resultList = new ArrayList(listSize);
                for (int j = 0; j < listSize; j++) {
                    resultList.add(null);
                }
                for (Iterator listIterator = listElement.getChildren().iterator(); listIterator.hasNext();) {
                    Element listItemElement = (Element) listIterator.next();
                    if (!ATTR_ITEM.equals(listItemElement.getName())) {
                        throw new InvalidDataException("Unable to read list item. Unknown element found: " + listItemElement.getName());
                    }
                    Object listItem;
                    String itemIndexString = listItemElement.getAttributeValue(ATTR_INDEX);
                    String itemClassString = listItemElement.getAttributeValue(ATTR_CLASS);
                    Class itemClass;
                    try {
                        itemClass = Class.forName(itemClassString);
                    } catch (ClassNotFoundException ex) {
                        throw new InvalidDataException("Unable to read list item: unable to load class: " + itemClassString + " \n" + ex.getMessage());
                    }
                    if (String.class.equals(itemClass)) {
                        listItem = listItemElement.getAttributeValue(ATTR_VALUE);
                    } else {
                        try {
                            listItem = itemClass.newInstance();
                        } catch (Exception ex) {
                            throw new InvalidDataException("Unable to create list item from class: " + itemClassString + " \n" + ex.getMessage());
                        }
                    }
                    for (Iterator itemIterator = listItemElement.getChildren().iterator(); itemIterator.hasNext();) {
                        Element valueElement = (Element) itemIterator.next();
                        if (!ATTR_VALUE.equals(valueElement.getName())) {
                            throw new InvalidDataException("Unable to read list item value. Unknown element found: " + listItemElement.getName());
                        }
                        if (listItem instanceof JDOMExternalizable) {
                            ((JDOMExternalizable) listItem).readExternal(valueElement);
                        } else {
                            DefaultJDOMExternalizer.readExternal(listItem, valueElement);
                        }
                    }
                    resultList.set(Integer.parseInt(itemIndexString), listItem);
                }
            }
        }
        if (resultList == null) {
            throw new InvalidDataException("Unable to read list. List element not found!");
        }
        myList = resultList;
    }

    public void writeExternal(Element element) throws WriteExternalException {
        int listSize = myList == null ? 0 : myList.size();
        Element listElement = new Element(ATTR_LIST);
        listElement.setAttribute(ATTR_LISTSIZE, Integer.toString(listSize));
        element.addContent(listElement);
        for (int i = 0; i < listSize; i++) {
            Object listItem = myList.get(i);
            if (listItem != null) {
                Element itemElement = new Element(ATTR_ITEM);
                itemElement.setAttribute(ATTR_INDEX, Integer.toString(i));
                itemElement.setAttribute(ATTR_CLASS, listItem.getClass().getName());
                if (listItem instanceof String) {
                    itemElement.setAttribute(ATTR_VALUE, (String) listItem);
                } else {
                    Element objectElement = new Element(ATTR_VALUE);
                    if (listItem instanceof JDOMExternalizable) {
                        ((JDOMExternalizable) listItem).writeExternal(objectElement);
                    } else {
                        DefaultJDOMExternalizer.writeExternal(listItem, objectElement);
                    }
                    itemElement.addContent(objectElement);
                }
                listElement.addContent(itemElement);
            }
        }
    }


}
