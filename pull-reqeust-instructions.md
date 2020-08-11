
Step-by-step Pull Request Creation Instructions
------------
1. Clone the repo.
    ssh-add ~/.ssh/id_gsa_github
	git clone git@github.com:18F/forms-as-a-service.git
2. [optionally] Get up to date with the 18F repo.
   git pull origin master
3. Create a new branch.  (Here we use the JIRA Issue Label.  Check with the pro.managers to see if maybe this should be named after some other JIRA thing...)
   git checkout -b FAAS-25
   git push origin FAAS-25
4. Go to the repository page under https://github.com/18F/forms-as-a-service and click on the **Pull Request** button.  Enter the JIRA ticket into the title for your pull request, and click the **Send** button.   Using the ticket in the title links the pull request to JIRA.
5. Teammates will do review your code and merge the pull request, but those details will be worked out later...

