/*
 * Copyright 2005-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.atlassian.maven.plugin.clover.samples.multiproject;

public class Simple1
{
    public void someMethod1()
    {
        int i = 0;
        if (i > 0)
        {
            int x = i;
            i = i + 1;
        }
    }

    public void someMethod2()
    {
        int i = 0;
        if (i > 0)
        {
            i = i + 1;
        }
    }
}
