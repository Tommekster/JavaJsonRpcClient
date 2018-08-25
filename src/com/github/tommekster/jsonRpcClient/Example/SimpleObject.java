
package com.github.tommekster.jsonRpcClient.Example;

import com.github.tommekster.jsonRpcClient.JsonRpcDataMember;

public class SimpleObject
{
    @JsonRpcDataMember(name = "name")
    public String objectName;
    
    @JsonRpcDataMember
    public String address;
    
    @JsonRpcDataMember
    public Long order;
}
