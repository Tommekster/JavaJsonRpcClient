# JAVA JSON RPC Client (Web2py)

This is very simple implementation of [JSON RPC](https://en.wikipedia.org/wiki/JSON-RPC) client-side for JAVA. 
The project contains a package with the library and a package with an example. 
The library uses [JSON.simple](https://code.google.com/archive/p/json-simple/) library. 

The JSON RPC server-side for examples is implemented in 
[Web2py](http://web2py.com/books/default/chapter/29/10/services#JSONRPC)
web framework. 

## Server-side example in Web2py

The implementation of JSON RPC server-side in Web2py is very easy. 
You just have to create new application in web2py and modify default controller
adding these functions: 

```python
def call():
    """
    exposes services. for example:
    http://..../[app]/default/call/jsonrpc
    decorate with @services.jsonrpc the functions to expose
    supports xml, json, xmlrpc, jsonrpc, amfrpc, rss, csv
    """
    return service()

@service.jsonrpc
def hello():
    return "Ahoj svete!"

@service.jsonrpc
def add(a,b):
    return a+b

@service.jsonrpc
def getSimpleObject():
    return dict(name='jmeno',address='addressa', order=123)

@service.jsonrpc
def getSimpleObjects(cnt):
    return [dict(name='jmeno'+str(i),address='addressa'+str(i), order = 13*i) for i in range(cnt)]

@service.jsonrpc
def getComplexObject():
    return dict(firstObject=getSimpleObject(), objectsList=getSimpleObjects(3))
```

## References

* [https://en.wikipedia.org/wiki/JSON-RPC]
* [https://www.tutorialspoint.com/json/json_java_example.htm]
* [https://code.google.com/archive/p/json-simple/]
* [http://web2py.com/books/default/chapter/29/10/services#JSONRPC]