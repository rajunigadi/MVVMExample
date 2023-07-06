# Avoid issuing warnings about things that exist before the pull request.
github.dismiss_out_of_range_messages

# Ensure the PR is not marked as DO NOT MERGE
fail("PR specifies label DO NOT MERGE") if github.pr_labels.any? { |label| label.include? "DO NOT MERGE" }

# Issue a warning if a pull request has WIP (Work in Progress) in the title.
warn('PR is classed as Work in Progress') if github.pr_title.include? '[WIP]'

# Add a CHANGELOG entry for app changes
if !git.modified_files.include?("CHANGELOG.md")
  fail("Please include a CHANGELOG entry. \nYou can find it at [CHANGELOG.md](https://github.com/rajunigadi/MVVMExample/CHANGELOG.md).")
  message "Note, we hard-wrap at 80 chars and use 2 spaces after the last line."
end

# unit testing
#junit_tests_dir = "path/to/your/results/**/*.xml"
#Dir[junit_tests_dir].each do |file_name|
#  junit.parse file_name
#  junit.show_skipped_tests = true
#  junit.report
#end

detektFile = String.new("detekt/detekt.xml")
message "detektFile file path: " + detektFile
if File.file?(detektFile)
    kotlin_detekt.report_file = detektFile
    kotlin_detekt.skip_gradle_task = true
    kotlin_detekt.severity = "warning"
    kotlin_detekt.filtering = true
    kotlin_detekt.detekt(inline_mode: true)
else
    message "detektFile not present"
end