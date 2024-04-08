#! /usr/bin/env python3

import sys
import os
import subprocess
import requests
import time
import yaml

dir = sys.argv[1]
gradle_options = sys.argv[2:]

with open(f"{dir}/end-to-end-test-config.yaml", 'r') as file:
    data = yaml.safe_load(file)

trigger_command = data["trigger_command"]
verify_urls = data["verify_urls"]

def run_gradle(subproject):
    print(f"Running {subproject}")
    command = ["./gradlew", f":{dir}:{subproject}:bootRun"] + gradle_options
    os.makedirs("./build", exist_ok=True)
    log_file = open(f"./build/{dir}-{subproject}.log", "w")
    return subprocess.Popen(command, stdin=subprocess.DEVNULL, stderr=log_file, stdout=log_file)


def start_services(subprojects, run_gradle):
    return list(map(run_gradle, subprojects))

def wait_for_healthy(ports):
    for port in ports:
        print(f"Waiting for {port} to be healthy...")
        while True:
            try:
                response = requests.get(f"http://localhost:{port}/actuator/health")
                if response.status_code == 200:
                    print(f"{port} is healthy")
                    break
            except requests.exceptions.RequestException:
                print(f"{port} is not healthy")
                time.sleep(1)


def trigger_sending():
    print(f"Triggering sending with {trigger_command}")
    command = trigger_command.split()
    process = subprocess.run(command, capture_output=True, text=True)
    if process.returncode != 0:
        print(f"Failed to send message {process.stderr}")
        sys.exit(1)

def wait_for_completion():
    for verify_url in verify_urls:
        print(f"Waiting for replies at {verify_url}")
        while True:
            response = requests.get(verify_url)

            if response.status_code != 200:
                print(f"Failed to get replies {response.status_code}")
                time.sleep(1)
            else:
                break

def stop_subprocesses(): 
    print("Stopping processes...")
    for process in subprocesses:
        process.terminate()
    print("Waiting for processes to finish...")
    for process in subprocesses:
        process.wait()

subprojects = list(filter(lambda file: "eventuate-" in file and not ("common" in file), os.listdir(dir)))

subprocesses = start_services(subprojects, run_gradle)

wait_for_healthy(list(range(8080, 8080 + len(subprojects))))

trigger_sending()

wait_for_completion()        

stop_subprocesses()        

print("SUCCESS!!!!")