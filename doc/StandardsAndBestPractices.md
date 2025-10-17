# Standards and best practices
This is a guide for coding standards and best practices
for the Evo+ project.

This project uses Git as a version control. Generally, the steps for 
implementing changes are (additional details are given below):

    1. Assign an issue or task to a developper from a project management software
    2. Automatically create a branch based on the issues or task
    3. Switch to the new branch with your favorite terminal
    4. Start implementing the new features
    5. Commit changes frequently
    6. Once complete, run local unit tests and fix* potential bugs
    7. Pull potential new changes and rebase new commits*
    8. Push changes to the repo
    9. If required, make additional fixes* if a build on GitLab has failed
    10. When a build is successfull, make a merge request
    11. Apply or discuss any changes* recommended by the code reviewer
    12. Once the merge is made, remove the ' wip' label from the issue

*(Any fixes or changes
should also follow step 5, 6, 7, and 8)


## Coding style
This project is primarily written in Java. As such, this project will comply to 
the [Google Java Style](https://google.github.io/styleguide/javaguide.html). 

The advantages of following Google's guideline are that they are widespread 
across many different projects while also benefitting from the existence of 
[tools and plugins](https://github.com/google/google-java-format) that can 
automatically 
reformat the code to comply with Google's standard.

As such, any developer contributing to this project shall format their code 
accordingly before submitting a pull request.

## Best practices

### Commits
Every change or new feature needs to be put into a commit on a newly created 
branch. Preferably, commits should be granular (i.e. small) in order to 
facilitate code review.

A commit can be made with the help of Git's Desktop App or with the following
commands:

`git checkout <name of branch>`

`git add <files to be commited>`

`git commit -m "Message that describes the changes made"`

The message that accompanies a commit should be descriptive enough to give a 
general understanding of what has changed. This is important for potentially
reverting a change or for bug fixing.

### Pushing commits
Before pushing commits (i.e. sending changes and new features) to the project's 
repository, the developer is required to:

>Run local tests and fix potential bugs
>
>Pull potential new commits from the repo and rebase local commits
>
>Run local tests and fix potential bugs

The command for pulling, rebasing, and pushing commits to the repo are:

`git pull` | `git pull --rebase`

`git push <repo>`

Once pushed, a build will automatically be launched. The state of the build is
indicated by a green checkmark (if successful) or a red cross (for a failure). 
If the build has failed, the developer is required to investigate why, 
implemented required changes to fix the problems with new commits, and push said
commits until a build is successfully made.

Once a build is successful, the developer can then proceed with a merge 
request.

### Merge request
Once the feature is deemed completed, free of bugs, and a build can 
successfully be made, the developer can proceed with creating a merge request. 

> Creating a merge request
>
> Select the squash commits option
> 
> Review code and implement recommendations
>
> Merge changes to relevant* branch and resolve conflicts

A merge request should be accompanied by a description that briefly describes 
how the feature or the fix was implemented. Additionally, a code review
should preferably be assigned to a different developer. Reviewing code
is obligatory to insure the quality of the code as well as sharing knowledge 
between developers.

A merge can be accomplished by either the developer or another person on the
development team. Conflicts should be carefully managed and integration tests 
will be run after a merge is made. If all the tests pass, a merge request can be closed. 
If not, the merge should be redone.

Once a merge request is completed, the branch where the feature was developed should
be automatically be deleted.

*Note: A branch should be merged into main only if there are no branches dependent on said branch.
If there are branches that depend on features implemented in another branch that was not yet merge into main, 
these branches should be merged into the branches that they depend on.

For example, if we have 3 branches, where Branch_2 depends on Branch_1:

```Main->Branch_1->Branch_2```

Then, Branch_2 should be merged into Branch_1 before Branch_1 is merge into Main.




