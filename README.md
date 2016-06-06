JavaTool<br/>
Joshua Wong
2013-10-26<br/>

Here's some tools under Java program I'm using at my work or my own project. Hope can help others.<br/>
It's open source so anyone can use or change or redistribute.<br/>
You can connect author at zhonghuafy@gmail.com<br/>

#######
The Json part based on <a href="https://github.com/stleary/JSON-java/tree/JSON-java-1.4">JSON-java</a>, and <a href="https://github.com/google/gson">Gson</a>. You should download it and put into /json/core. And thanks to their work!<br/>

/action/BasicAction.java Basic action class extends com.opensymphony.xwork2.ActionSupport. Can write a json to client side, get client side's IP address, kick sombody out of the session and a simple example of export excel.<br/>
/door/Boot.java an example which extends implements ServletContextListener for loading and read properties from property file.<br/>
/door/SessionFilter.java implements javax.servlet.Filter filter users who had not loged in.<br/>
/door/SessionListener.java implements javax.servlet.http.HttpSessionListener create or destroy session.<br/>
/json/tools/JsonTool.java a json tool based com.google.gson.Gson.<br/>
/json/tools/JsonUtil.java a json tool based JSON-java.<br/>
/tools/BOConverter.java convert an object to a byte array or revert.<br/>
/tools/GridIndex.java extjs grid paging tool.<br/>
/tools/replace html symbol to codes.<br/>
/tools/LabelValue.java label value pair.<br/>
/tools/Property.java a class to handle properties config files.<br/>
/tools/ReadProperty.java a class to read properties in config file.<br/>
/tools/Record.java an encapsulation of an integer and a list for read records from data table.<br/>
/tools/StackTrace.java print exception information to console or logfile.<br/>
/tools/TreeNode.java used to generate trees.<br/>
