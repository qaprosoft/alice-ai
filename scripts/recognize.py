import urllib
import subprocess
import sys
import os
import argparse

#print "\nOur deep learning model is processing your image ..."

os.chdir(sys.path[0])

help_string = "recognize.py"

# values of the dict -> [load, gpu, cfg]
models = {
    "mlb":[7600, 0.9, "tiny-ai-weights"],
    "default":[-1, 0.9, "tiny-ai-weights-lc-mlb"]
}

parser = argparse.ArgumentParser(help_string)
parser.add_argument("--model", choices=models.keys(), default="default", help="choose model among {}".format(models.keys()))
parser.add_argument("--folder", help="enter path to folder for recognition")
parser.add_argument("--output", help="enter type of output file", default="json", choices=["json", "xml", "img"])

args = parser.parse_args()
model = args.model
FOLDER = args.folder
OUTPUT = args.output

if FOLDER == None:
    print "There is not folder for recognition! Please, use -h flag for more imformation"
    sys.exit(1)
#TODO: verify that provided folder exists preliminary!

# darflow evaluation parameters
gpu = models[model][1]
#print "gpu: " + gpu
load = models[model][0]
#print "load: " + load
model_name = models[model][2]
print "model_name: " + model_name
print "FOLDER: " + FOLDER
print "OUTPUT: " + OUTPUT

model_path = "/home/ubuntu/darkflow/flow"
print "model_path: " + model_path

# One script screates json, the other  - images with bounding boxes
generate_json = "{} --test {} --backup /home/ubuntu/darkflow/ckpt/mlb_branch/ --load {} --model /home/ubuntu/darkflow/cfg/{}.cfg --json".format(model_path, FOLDER, load, model_name)
print generate_json
generate_img = "{} --test {} --backup /home/ubuntu/darkflow/ckpt/mlb_branch/ --load {} --model /home/ubuntu/darkflow/cfg/{}.cfg".format(model_path, FOLDER, load, model_name)
print generate_img
generate_xml = "python to_xml_output.py --json " + FOLDER + "/out/ --save " + FOLDER + "/out/"
print generate_xml

# json and img generations can be launched in background
if OUTPUT=="json":
    subprocess.call(generate_json, shell=True)
if OUTPUT=="img":
    subprocess.call(generate_img, shell=True)
if OUTPUT=="xml":
    subprocess.call(generate_json, shell=True)
    subprocess.call(generate_xml, shell=True)
