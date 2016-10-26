# BoomUpload
This plugin helps you to upload aar or jar file into the nexus.  
#Usage  
In project's build.gradle file, add the dependencies:
```
classpath 'com.wkf.boomnexus:nexusupload:1.0'
```
Next, configure the library's build.gradle file.
apply the plugin:
```
apply plugin: 'com.wkf.nexusupload'
```
add the following:
```
nexus {
    sign = false
    repositoryUrl = 'your repository url'
    snapshotRepositoryUrl = 'your snapshot repository url'
}
```
Then, configure the username and password:
In "~/.gradle/gradle.properties",add the following code:
```
nexusUsername = your nexus username
nexusPassword = your nexus password
```
Oh, do not forget configure the group and version(in library's build.gradle) 
```
version 'your verion number'
group 'your group name'
```
After the configuration, aync the gradle task, and then click the "uploadArchives" task, wait for a second, then, boom! upload success!


