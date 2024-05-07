# jenkins-shared-library

As Pipeline is adopted for more and more projects in an organization, common patterns are likely to emerge. Oftentimes it is useful to share parts of Pipelines between various projects to reduce redundancies and keep code "DRY" [1].

Pipeline has support for creating "Shared Libraries" which can be defined in external source control repositories and loaded into existing Pipelines.

Ref : https://www.jenkins.io/doc/book/pipeline/shared-libraries/

Organize common pipeline logic into reusable shared libraries. This keeps your Jenkinsfiles clean and concise, promoting maintainability.

* Define shared libraries in Groovy scripts or external repositories.
* Import libraries using @Library or libraries blocks in your pipelines.
* Reference shared functions and variables within your pipeline stages.

We are going to idenfiy all the common patterns and will place everything in this repository



