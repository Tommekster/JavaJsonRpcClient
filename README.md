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
    return "Hello world!"

@service.jsonrpc
def add(a,b):
    return a+b

@service.jsonrpc
def getSimpleObject():
    return dict(name='NaMe',address='domificile', order=123)

@service.jsonrpc
def getSimpleObjects(cnt):
    return [dict(name='NaMe'+str(i),address='domificile'+str(i), order = 13*i) for i in range(cnt)]

@service.jsonrpc
def getComplexObject():
    return dict(firstObject=getSimpleObject(), objectsList=getSimpleObjects(3))

@service.jsonrpc
def getMonths():
    return ['january', 'february', 'march', 'April', 'May', '...']

@service.jsonrpc
def getObjectWithDate():
    return dict(created='2018-08-20')
```

## References

* [https://en.wikipedia.org/wiki/JSON-RPC]
* [https://www.tutorialspoint.com/json/json_java_example.htm]
* [https://code.google.com/archive/p/json-simple/]
* [http://web2py.com/books/default/chapter/29/10/services#JSONRPC]