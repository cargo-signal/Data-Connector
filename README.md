
<style type="text/css">
  .sectionHeader {
    color: "#0366d6"
  }
</style>
<h1 align="center">ADD CARGO SIGNAL IMAGE HERE</h1>
<h1 align="center">Cargo Signal BI Data Connector</h1>

<h3 align="center">
Open Source Connector for Cargo Signal Business Intelligence
</h3>

<hr/>

<h2>
<a href="#id1">1&nbsp;&nbsp;&nbsp;About this document</a>
  <p>The Data Connector is a sample solution to demonstrate how to leverage Cargo Signal APIs, cloud storage and BI tools to better understand your shipments through analytics.  The solution provides for retrieving completed shipments along with the associated telemetry and alert data. This data is pushed to Azure, where a PowerBI can retrieve the data and provide a dashboard representing your completed shipments.</p>
  <p>The solution leverages Cargo Signal APIs, Java 8, Microsoft Azure and Microsoft Power BI.  If your organization prefers C# or Python for the implementation language or perhaps Amazon Web Services over Azure, you should be able to use this solution as a reference solution to customize and build the solution that best fits your organization's environment and technology stack.</p>
</h2>

<h2>
<a href="#id2">2&nbsp;&nbsp;&nbsp;Prerequisites</a>
  <p>To use the sample application, you will need:</p>
  <ul>
    <li>Cargo Signal API client key</li>
    <li>Development IDE</li>
    <li>Java 8</li>
    <li>Microsoft Azure Account</li>
    <li>Microsoft Power BI</li>
  <ul>
</h2>

<h2>
  <a href="#id4">4&nbsp;&nbsp;&nbsp;How Does It Work</a>
  <p>INSERT DIAGRAM HERE
</h2>

<h2>
<a href="#id4">4&nbsp;&nbsp;&nbsp;Usage</a>
  <p>The Data Connector exposes two HTTP endpoints along with a scheduled timer:</p>
  <ul>
    <li></li>
  </ul>
</h2>

<h2>
  <p>While any code editor will work, Visual Studio Code and IntelliJ offer additional built-in Azure and Java functionality that you will likely find helpful.</p>
<a href="#id5">5&nbsp;&nbsp;&nbsp;IDE Setup</a>
  <h3>Visual Studio Code <a href="https://code.visualstudio.com/download">Download VS Code</a></h3>
  <h3>IntelliJ <a href="https://www.jetbrains.com/idea/download/">Download IntelliJ</a></h3>
</h2>

<h2>
<a href="#id6">6&nbsp;&nbsp;&nbsp;Tips</a>
</h2>


## Usage

```http
GET https://dev-app.cargosignal.com/connector/shipments?minDate=2020-08-27T00:00:00.000Z
```

```json
{
  "json": "goes here"
}
```


## FAQ's

* If you have any questions or corrections, please open an issue and we'll get it merged ASAP
* For any other questions or concerns, just shoot me an email
