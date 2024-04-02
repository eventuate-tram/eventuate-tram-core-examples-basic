#! /bin/bash -e

cat > generated_config.yml <<END
version: 2.1
orbs:
  eventuate-gradle-build-and-test: "eventuate_io/eventuate-gradle-build-and-test@0.2.9"
workflows:
  version: 2.1
  build-test-and-deploy:
    jobs:
END

for build_script in scripts/build-and-test-all*.sh ; do
build_script_name=$(echo $build_script | sed -e 's/\.sh//' -e 's/build-and-test-all-//' -e 's?scripts/??')
cat >> generated_config.yml <<END
      - eventuate-gradle-build-and-test/build-and-test:
          name: $build_script_name
          script: ./$build_script
          machine_image: ubuntu-2204:2023.07.2
          java_version_to_install: '17'

END
done
