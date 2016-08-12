/*
 * ====================================================================
 * 
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2000-2003 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Jakarta Element Construction Set", 
 *    "Jakarta ECS" , and "Apache Software Foundation" must not be used 
 *    to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Jakarta Element Construction Set" nor "Jakarta ECS" nor may "Apache" 
 *    appear in their names without prior written permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
 
package org.apache.ecs.wml;

import org.apache.ecs.*;

/**
 * This represents a &lt;onevent&gt; element.
 */
public class Onevent extends org.apache.ecs.MultiPartElement implements org.apache.ecs.Printable
{
   /**
        Private initialization routine.
    */
    {
        setElementType("onevent");
    }

    /**
        Basic constructor. You need to set the attributes using the
        set* methods.
    */
    public Onevent()
    {
    }

    /**
        Use the set* methods to set the values
        of the attributes.

        @param   element  set the value of &lt;H1&gt;value&lt;/H1&gt;
    */
    public Onevent(org.apache.ecs.Element element)
    {
        addElement(element);
    }

    /**
        Use the set* methods to set the values
        of the attributes.

        @param   value  set the value of &lt;H1&gt;value&lt;/H1&gt;
    */
    public Onevent(String value)
    {
        addElement(value);
    }

    /**
        Adds an Element to the element.
        @param  hashcode name of element for hash table
        @param  element Adds an Element to the element.
     */
    public Onevent addElement(String hashcode,org.apache.ecs.Element element)
    {
        addElementToRegistry(hashcode,element);
        return(this);
    }

    /**
        Adds an Element to the element.
        @param  hashcode name of element for hash table
        @param  element Adds an Element to the element.
     */
    public Onevent addElement(String hashcode,String element)
    {
        addElementToRegistry(hashcode,element);
        return(this);
    }

    /**
        Adds an Element to the element.
        @param  element Adds an Element to the element.
     */
    public Onevent addElement(org.apache.ecs.Element element)
    {
        addElementToRegistry(element);
        return(this);
    }
    /**
        Adds an Element to the element.
        @param  element Adds an Element to the element.
     */
    public Onevent addElement(String element)
    {
        addElementToRegistry(element);
        return(this);
    }
    /**
        Removes an Element from the element.
        @param hashcode the name of the element to be removed.
    */
    public Onevent removeElement(String hashcode)
    {
        removeElementFromRegistry(hashcode);
        return(this);
    }

//------------------------Attributes
                    /**
     * Get the Type attribute.
     */
    public String getType()
    {
        return getAttribute("type");
    }
  
    /**
     * Set the Type attribute.
     */
    public Onevent setType(String value)
    {
        addAttribute("type",value);
        return this;
    }
                    /**
     * Get the Id attribute.
     */
    public String getId()
    {
        return getAttribute("id");
    }
  
    /**
     * Set the Id attribute.
     */
    public Onevent setId(String value)
    {
        addAttribute("id",value);
        return this;
    }
                    /**
     * Get the Classname attribute.
     */
    public String getClassname()
    {
        return getAttribute("class");
    }
  
    /**
     * Set the Classname attribute.
     */
    public Onevent setClassname(String value)
    {
        addAttribute("class",value);
        return this;
    }
}
