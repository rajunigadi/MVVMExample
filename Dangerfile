# Avoid issuing warnings about things that exist before the pull request.
github.dismiss_out_of_range_messages

# Issue a warning if a pull request has WIP (Work in Progress) in the title.
warn('PR is classed as Work in Progress') if github.pr_title.include? '[WIP]'

# unit testing
#junit_tests_dir = "path/to/your/results/**/*.xml"
#Dir[junit_tests_dir].each do |file_name|
#  junit.parse file_name
#  junit.show_skipped_tests = true
#  junit.report
#end

# android lints
#android_lint.report_file = "app/build/reports/lint-results-debug.xml"
#android_lint.filtering = true
#android_lint.skip_gradle_task = true
#android_lint.lint(inline_mode: true)

# ktlint
#checkstyle_format.base_path = Dir.pwd
#checkstyle_format.report 'app/build/reports/ktlint/ktlintMainSourceSetCheck/ktlintMainSourceSetCheck.xml'

# To support multi module projects, identify defined modules via the settings.gradle.kts
# Open the file `settings.gradle.kts`
File.open("settings.gradle.kts", "r") do |file_handle|
  # Read through every single file
  file_handle.each_line do |setting|
    # Only check lines which include `include`
    if setting.include? "include"
        # read in the module name
        # Note this goes by the assumption that name equals path
        gradleModule = setting[10, setting.length-12]

        # Process check-style results
        checkstyleFile = String.new(gradleModule + "/build/reports/checkstyle/checkstyle.xml")
        if File.file?(checkstyleFile)
            checkstyle_format.base_path = Dir.pwd
            checkstyle_format.report(checkstyleFile, inline_mode: true)
        end

        # Process Android-Lint results
        androidLintFile = String.new(gradleModule + "/build/reports/lint-results.xml")
        androidLintDebugFile = String.new(gradleModule + "/build/reports/lint-results-debug.xml")
        if File.file?(androidLintFile) || File.file?(androidLintDebugFile)
            android_lint.skip_gradle_task = true # do this if lint was already run in a previous build step
            android_lint.severity = "Warning"
            if File.file?(androidLintFile)
                android_lint.report_file = androidLintFile
            else
                android_lint.report_file = androidLintDebugFile
            end
            android_lint.filtering = true
            android_lint.lint(inline_mode: true)
        end

        # Process Detekt results
        detektFile = String.new(gradleModule + "/build/reports/detekt.xml")
        if File.file?(detektFile)
            kotlin_detekt.report_file = detektFile
            kotlin_detekt.skip_gradle_task = true
            kotlin_detekt.severity = "warning"
            kotlin_detekt.filtering = true
            kotlin_detekt.detekt(inline_mode: true)
        end
    end
  end
end