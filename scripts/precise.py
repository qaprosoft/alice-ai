import os
import sys
import codecs
import json
import xml.etree.ElementTree as ET
import numpy as np
import argparse
import datetime
import datetime
from pytz import timezone
import pytz
import glob

#/home/ubuntu/tmp/data_mlb/test/ann/
# /home/ubuntu/tmp/data_mlb/test/out/

parser = argparse.ArgumentParser()
parser.add_argument("--truth", default="/home/ubuntu/tools/alice/data/ideal/test/ann/", help="path to ground truth annotations folder")
parser.add_argument("--predicted", default="/home/ubuntu/tools/alice/data/ideal/test/out/", help="path to predicted boxes folder")
parser.add_argument("--threshold", default=0.5, help="threshold for IOU score")
parser.add_argument("--center", default=True, help="Whether to show Center Score")
parser.add_argument("--iou", default=False, help="Whether to show IOU Score")
args = parser.parse_args()


#Methods definitions
def isCenterMatch(gt, pred):

    # predicted coordinates
    xmin_p = pred[2]
    ymin_p = pred[3]
    upLeftPoint  = [xmin_p, ymin_p]
    #predictedPoints.append(upLeftPoint)

    xmax_p = pred[4]
    ymax_p = pred[5]
    bottomRightPoint  = [xmax_p, ymax_p]
    #predictedPoints.append(bottomRightPoint)


    """
    print "xmin_t = {}, ".format(xmin_t, xmin_p)
    print "ymin_t = {}".format(ymin_t, ymin_p)
    print "xmax_t = {} and xmax_p = {}".format(xmax_t, xmax_p)
    print "ymax_t = {} and ymax_p = {}".format(ymax_t, ymax_p)
    """

    predCenter = (int((xmax_p - xmin_p)/2.0+xmin_p), int((ymax_p - ymin_p)/2.0)+ymin_p)

    # true coordinates
    xmin_t = gt[2]
    ymin_t = gt[3]
    xmax_t = gt[4]
    ymax_t = gt[5]

    gtCenter = (int((xmax_t - xmin_t)/2.0+xmin_t), int((ymax_t - ymin_t)/2.0)+ymin_t)

    if isPointInsideRect(predCenter, gt) or isPointInsideRect(gtCenter, pred):
        return [1, predCenter, gtCenter]
    else:
        return [0, predCenter, gtCenter]

def isIntersects(pred, gt):

    predictedPoints = []
    gtPoints = []

    # predicted coordinates
    xmin_p = pred[2]
    ymin_p = pred[3]
    upLeftPoint  = [xmin_p, ymin_p]
    predictedPoints.append(upLeftPoint)

    xmax_p = pred[4]
    ymax_p = pred[5]
    bottomRightPoint  = [xmax_p, ymax_p]
    predictedPoints.append(bottomRightPoint)

    e_xmin_p = pred[2]
    e_ymin_p = pred[5]
    bottomLeftPoint = [e_xmin_p, e_ymin_p]
    predictedPoints.append(bottomLeftPoint)


    e_xmax_p = pred[4]
    e_ymax_p = pred[3]
    upRightPoint = [e_xmax_p, e_ymax_p]
    predictedPoints.append(upRightPoint)

    # true coordinates
    xmin_t = gt[2]
    ymin_t = gt[3]
    upLeftPoint  = [xmin_t, ymin_t]
    gtPoints.append(upLeftPoint)

    xmax_t = gt[4]
    ymax_t = gt[5]
    bottomRightPoint  = [xmax_t, ymax_t]
    gtPoints.append(bottomRightPoint)

    e_xmin_t = gt[2]
    e_ymin_t = gt[5]
    bottomLeftPoint = [e_xmin_t, e_ymin_t]
    gtPoints.append(bottomLeftPoint)

    e_xmax_t = gt[4]
    e_ymax_t = gt[3]
    upRightPoint = [e_xmax_t, e_ymax_t]
    gtPoints.append(upRightPoint)

    boool = False
    for point in predictedPoints:
        if isPointInsideRect(point, gt):
            boool = True
            return boool
    for point in gtPoints:
        if isPointInsideRect(point, pred):
            boool = True
            return boool
    return boool

def isPointInsideRect(point, rect):

    x, y = point[0], point[1]

    xmin = rect[2]
    ymin = rect[3]
    xmax = rect[4]
    ymax = rect[5]


    if xmin<=x and ymin<=y and xmax>=x and ymax>=y:
        return True
    else:
        return False

def isCenterMatch(gt, pred):

    # predicted coordinates
    xmin_p = pred[2]
    ymin_p = pred[3]
    upLeftPoint  = [xmin_p, ymin_p]
    #predictedPoints.append(upLeftPoint)

    xmax_p = pred[4]
    ymax_p = pred[5]
    bottomRightPoint  = [xmax_p, ymax_p]
    #predictedPoints.append(bottomRightPoint)


    """
    print "xmin_t = {}, ".format(xmin_t, xmin_p)
    print "ymin_t = {}".format(ymin_t, ymin_p)
    print "xmax_t = {} and xmax_p = {}".format(xmax_t, xmax_p)
    print "ymax_t = {} and ymax_p = {}".format(ymax_t, ymax_p)
    """

    predCenter = (int((xmax_p - xmin_p)/2.0+xmin_p), int((ymax_p - ymin_p)/2.0)+ymin_p)

    # true coordinates
    xmin_t = gt[2]
    ymin_t = gt[3]
    xmax_t = gt[4]
    ymax_t = gt[5]

    gtCenter = (int((xmax_t - xmin_t)/2.0+xmin_t), int((ymax_t - ymin_t)/2.0)+ymin_t)

    if isPointInsideRect(predCenter, gt):
        return [1, predCenter, gtCenter]
    else:
        return [0, predCenter, gtCenter]

def find_intersection(coord_pred, coord_gt):
    """
    coord_pred, coord_gt - [filename 0,label 1,xmin 2,ymin 3,xmax 4, ymax 5]
    returns list [intersection_square, ]
    """

    width = 0
    height = 0

    #print "Predicted {}".format(coord_pred)
    #print "Ground truth {}".format(coord_gt)

    # predicted coordinates
    xmin_p = coord_pred[2]
    ymin_p = coord_pred[3]
    xmax_p = coord_pred[4]
    ymax_p = coord_pred[5]

    e_xmin_p = coord_pred[2]
    e_ymin_p = coord_pred[5]
    e_xmax_p = coord_pred[4]
    e_ymax_p = coord_pred[3]

    # true coordinates
    xmin_t = coord_gt[2]
    ymin_t = coord_gt[3]
    xmax_t = coord_gt[4]
    ymax_t = coord_gt[5]

    e_xmin_t = coord_gt[2]
    e_ymin_t = coord_gt[5]
    e_xmax_t = coord_gt[4]
    e_ymax_t = coord_gt[3]

    #print "xmin_t = {} and xmin_p = {}".format(xmin_t, xmin_p)
    #print "ymin_t = {} and ymin_p = {}".format(ymin_t, ymin_p)
    #print "xmax_t = {} and xmax_p = {}".format(xmax_t, xmax_p)
    #print "ymax_t = {} and ymax_p = {}".format(ymax_t, ymax_p)


    if isIntersects(gt=coord_gt, pred=coord_pred):
        # if ground truth box is inside of the predicted box (Intersection variant #1)
        if xmin_t >= xmin_p and ymin_t >= ymin_p and ymax_t <= ymax_p and xmax_t <= xmax_p:

            #print "#1"
            #intersection coordinates
            xmin_intersection = xmin_t
            ymin_intersection = ymin_t
            xmax_intersection = xmax_t
            ymax_intersection = ymax_t

            #print "I am in IF gt"
            width = xmax_t - xmin_t
            height = ymax_t - ymin_t

        # if predicted box is inside of the ground truth box (Intersection variant #1)
        if xmin_t <= xmin_p and ymin_t <= ymin_p and ymax_t >= ymax_p and xmax_t >= xmax_p:

            #print "#2"
            #intersection coordinates
            xmin_intersection = xmin_p
            ymin_intersection = ymin_p
            xmax_intersection = xmax_p
            ymax_intersection = ymax_p

            #print "I am in IF pred"
            width = xmax_p - xmin_p
            height = ymax_p - ymin_p

        # (Intersection variant #2) predicted box is higher
        if xmin_t >= xmin_p and ymin_t >= ymin_p and ymax_t >= ymax_p and xmax_t >= xmax_p:

            #print "#3"
            #intersection coordinates
            xmin_intersection = xmin_t
            ymin_intersection = ymin_t
            xmax_intersection = xmax_p
            ymax_intersection = ymax_p

            #print "I am in IF pred"
            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        # (Intersection variant #2) ground truth box is higher
        if xmin_t <= xmin_p and ymin_t <= ymin_p and ymax_t <= ymax_p and xmax_t <= xmax_p:

            #print "#4"
            #intersection coordinates
            xmin_intersection = xmin_p
            ymin_intersection = ymin_p
            xmax_intersection = xmax_t
            ymax_intersection = ymax_t


            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        # (Intersection variant #3) ground truth box is higher
        if xmin_t >= xmin_p and ymin_t <= ymin_p and ymax_t <= ymax_p and xmax_t >= xmax_p:


            #intersection coordinates
            #print "#5"
            xmin_intersection = e_xmin_t # ok
            ymin_intersection = e_ymax_p
            xmax_intersection = e_xmax_p # ok
            ymax_intersection = e_ymin_t

            #cv2.line(img, (xmax_intersection, ymax_intersection), ((xmax_intersection, ymax_intersection)) , (255,255,255), 9)

            """
            print "xmin_t = {}".format(xmin_t, xmin_p)
            print "ymin_t = {}".format(ymin_t, ymin_p)
            print "xmax_t = {} and xmax_p = {}".format(xmax_t, xmax_p)
            print "ymax_t = {} and ymax_p = {}".format(ymax_t, ymax_p)
            """

            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        # (Intersection variant #6) predicted box is higher
        if xmin_t <= xmin_p and ymin_t >= ymin_p and ymax_t >= ymax_p and xmax_t <= xmax_p:


            #intersection coordinates
            #print "#6"
            xmin_intersection = e_xmin_p # ok
            ymin_intersection = e_ymax_t
            xmax_intersection = e_xmax_t
            ymax_intersection = e_ymin_p

            #cv2.line(img, (xmax_intersection, ymax_intersection), ((xmax_intersection, ymax_intersection)) , (255,255,255), 9)

            """
            print "xmin_t = {}".format(xmin_t, xmin_p)
            print "ymin_t = {}".format(ymin_t, ymin_p)
            print "xmax_t = {} and xmax_p = {}".format(xmax_t, xmax_p)
            print "ymax_t = {} and ymax_p = {}".format(ymax_t, ymax_p)
            """

            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        # (Intersection variant #7)
        if xmin_t >= xmin_p and ymin_t >= ymin_p and ymax_t >= ymax_p and xmax_t <= xmax_p:

            #print "#7"
            #intersection coordinates
            xmin_intersection = xmin_t
            ymin_intersection = ymin_t
            xmax_intersection = xmax_t
            ymax_intersection = ymax_p

            #print "I am in IF pred"
            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        # (Intersection variant #8)
        if xmin_t <= xmin_p and ymin_t <= ymin_p and ymax_t <= ymax_p and xmax_t >= xmax_p:

            """
            coord1 = ["f", "truth", 591, 74, 744, 90]
            coord2 = ["f", "predicted", 582, 76, 759, 97]
            """

            #print "#8"
            #intersection coordinates
            xmin_intersection = xmin_p
            ymin_intersection = ymin_p
            xmax_intersection = xmax_p
            ymax_intersection = ymax_t

            #print "I am in IF pred"
            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        intersection_square = width*height

        # (Intersection variant #9)
        if xmin_t >= xmin_p and ymin_t >= ymin_p and ymax_t <= ymax_p and xmax_t >= xmax_p:

            #print "#9"
            #intersection coordinates
            xmin_intersection = xmin_t
            ymin_intersection = ymin_t
            xmax_intersection = xmax_p
            ymax_intersection = ymax_t

            #print "I am in IF pred"
            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        # (Intersection variant #10)
        if xmin_t <= xmin_p and ymin_t <= ymin_p and ymax_t >= ymax_p and xmax_t <= xmax_p:

            #print "#10"
            #intersection coordinates
            xmin_intersection = xmin_p
            ymin_intersection = ymin_p
            xmax_intersection = xmax_t
            ymax_intersection = ymax_p

            #print "I am in IF pred"
            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        # (Intersection variant #11)
        if xmin_t >= xmin_p and ymin_t <= ymin_p and ymax_t <= ymax_p and xmax_t <= xmax_p:

            #print "#10"
            #intersection coordinates
            xmin_intersection = xmin_t
            ymin_intersection = ymin_p
            xmax_intersection = xmax_t
            ymax_intersection = ymax_t

            #print "I am in IF pred"
            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        # (Intersection variant #12)
        if xmin_t <= xmin_p and ymin_t >= ymin_p and ymax_t >= ymax_p and xmax_t >= xmax_p:

            #print "#12"
            #intersection coordinates
            xmin_intersection = xmin_p
            ymin_intersection = ymin_t
            xmax_intersection = xmax_p
            ymax_intersection = ymax_p

            #print "I am in IF pred"
            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        # (Intersection variant #13)
        if xmin_t <= xmin_p and ymin_t >= ymin_p and ymax_t <= ymax_p and xmax_t <= xmax_p:

            #print "#13"
            #intersection coordinates
            xmin_intersection = xmin_p
            ymin_intersection = ymin_t
            xmax_intersection = xmax_t
            ymax_intersection = ymax_t

            #print "I am in IF pred"
            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        # (Intersection variant #14)
        if xmin_t >= xmin_p and ymin_t <= ymin_p and ymax_t >= ymax_p and xmax_t >= xmax_p:

            #print "#14"
            #intersection coordinates
            xmin_intersection = xmin_t
            ymin_intersection = ymin_p
            xmax_intersection = xmax_p
            ymax_intersection = ymax_p

            #print "I am in IF pred"
            width = xmax_intersection - xmin_intersection
            height = ymax_intersection - ymin_intersection

        intersection_square = width*height
    else:
        intersection_square = width*height
        xmin_intersection = 0
        ymin_intersection = 0
        xmax_intersection = 0
        ymax_intersection = 0
    return [intersection_square, xmin_intersection, ymin_intersection, xmax_intersection, ymax_intersection]

def find_square(coords):
    """
    coords - [filename, label,xmin,ymin,xmax,ymax]
    returns square for rectangle with given coordinates (coords)
    """
    xmin = coords[2]
    ymin = coords[3]
    xmax = coords[4]
    ymax = coords[5]
    width = xmax - xmin
    height = ymax - ymin
    return width*height

def IOU(coord_pred, coord_gt):
    """
    returns intersection over union for rectangles with given coordinates
    """
    pred_square = find_square(coord_pred) #square of predicted box
    gt_square = find_square(coord_gt) # square of ground truth box
    sum_of_squares = pred_square+gt_square

    intersection = find_intersection(coord_pred, coord_gt)
    union = sum_of_squares - intersection[0]
    #print "Intersection = {}".format(intersection)
    #print "Union = {}".format(union)
    return float(intersection[0])/union


# Parse xml annotations
ann_folder = args.truth
dumps_xml = {}
#print "XML parsing started..."
for file in glob.glob(ann_folder + '/' + '*.xml', recursive=True):
    annotation_file = open(file)
    tree = ET.parse(annotation_file)
    root = tree.getroot()
    
    filename = str(root.find('filename').text).rsplit('.', 1)[0]
    #print filename
    all = list()

    for obj in root.iter('object'):
        current = list()
        label = obj.find('name').text

        xmlbox = obj.find('bndbox')
        xmin = int(float(xmlbox.find('xmin').text))
        xmax = int(float(xmlbox.find('xmax').text))
        ymin = int(float(xmlbox.find('ymin').text))
        ymax = int(float(xmlbox.find('ymax').text))
        
        current = (filename, label, xmin, ymin, xmax, ymax)
        dumps_xml[current] = 0
    annotation_file.close()
#print "XML parsing finished."
#print "XML dump"
#print dumps_xml

# Parse recognized metadata from json format
json_folder = args.predicted
dumps_json = {}
#print "JSON parsing started..."
for file in glob.glob(json_folder + '/' + '*.json', recursive=True):
    try:
        config = json.loads(codecs.open(file, "r", encoding='utf-8', errors='ignore').read())
    except ValueError:
        print "{} does not have any bounding box".format(file)
        continue

    for dictionary in config:
        label = dictionary["label"]
        xmin = dictionary["topleft"]["x"]
        ymin = dictionary["topleft"]["y"]
        xmax = dictionary["bottomright"]["x"]
        ymax = dictionary["bottomright"]["y"]
        
        filename = os.path.basename(file).rsplit('.', 1)[0]
        current = [filename, label, xmin, ymin, xmax, ymax]
        current = (filename, label, xmin, ymin, xmax, ymax)
        dumps_json[current] = 0

#print "JSON parsing finished."
#print "JSON dump"
#print dumps_json

#True Positive count (TP), False Positive count (FP), True Negative count (TN)
#TODO: refactor to read from labels.txt. Reading from local folder is fine as well
classes = ["button","checkbox","Baltimore_Orioles","radio", "text_field", "select"]

TP = 0
FP = 0
TN = 0
button_measures = [TP, FP, TN]
checkbox_measures = [TP, FP, TN]
date_picker_measures = [TP, FP, TN]
radio_measures = [TP, FP, TN]
text_field_measures = [TP, FP, TN]
select_measures = [TP, FP, TN]
measures = [button_measures, checkbox_measures, date_picker_measures, radio_measures, text_field_measures, select_measures]

#TODO: refactor list dict declaration using captions from classes and measures from measures
class_list_dict = {
    "button": button_measures,
    "checkbox": checkbox_measures,
    "Baltimore_Orioles": date_picker_measures,
    "radio": radio_measures,
    "text_field": text_field_measures,
    "select": select_measures
}


#Intersection over union IOU
threshold = args.threshold
for predicted_metadata in dumps_json.keys():
    for actual_metadata in dumps_xml.keys():
        #compare objects only from the same file
        if predicted_metadata[0] == actual_metadata[0]:
            iou_value = IOU(coord_gt = actual_metadata, coord_pred = predicted_metadata)
            if iou_value > threshold:
                # 1 - label/name of element
                #TODO: improve measurement data structures to be able to support scalable classes (up to 10K)
                if predicted_metadata[1] == actual_metadata[1] == classes[0]:
                    button_measures[0] += 1
                elif predicted_metadata[1] == actual_metadata[1] == classes[1]:
                    checkbox_measures[0] += 1
                elif predicted_metadata[1] == actual_metadata[1] == classes[2]:
                    date_picker_measures[0] += 1
                elif predicted_metadata[1] == actual_metadata[1] == classes[3]:
                    radio_measures[0] += 1
                elif predicted_metadata[1] == actual_metadata[1] == classes[4]:
                    text_field_measures[0] += 1
                elif predicted_metadata[1] == actual_metadata[1] == classes[5]:
                    select_measures[0] += 1
                else:
                    class_list_dict[actual_metadata[1]][1] += 1
            else:
                class_list_dict[actual_metadata[1]][1] += 1


AP_list = []
for z in class_list_dict.keys():
    TP = class_list_dict[z][0]
    #print "TP - {}".format(TP)
    FP = class_list_dict[z][1]
    #print "FP - {}".format(FP)
    if TP != 0 and FP != 0:
        AP = float(TP)/(TP+FP)
        class_list_dict[z].append(AP)
        AP_list.append(AP)
IOU_mAP = np.mean(AP_list)

# mAP with centers
TP = 0
FP = 0
TN = 0
button_measures = [TP, FP, TN]
checkbox_measures = [TP, FP, TN]
date_picker_measures = [TP, FP, TN]
radio_measures = [TP, FP, TN]
text_field_measures = [TP, FP, TN]
select_measures = [TP, FP, TN]
measures = [button_measures, checkbox_measures, date_picker_measures, radio_measures, text_field_measures, select_measures]

class_list_dict = {
    "button": button_measures,
    "checkbox": checkbox_measures,
    "Baltimore_Orioles": date_picker_measures,
    "radio": radio_measures,
    "text_field": text_field_measures,
    "select": select_measures
}


for item_j in dumps_json.keys():
    for item_x in dumps_xml.keys():
        #print str(dumps_xml[item_x]) + dumps_xm
        #print "item_j - {}".format(item_j)
        #print "item_x - {}".format(item_x)
        if item_j[0] == item_x[0] and dumps_xml[item_x] == 0:
            if isCenterMatch(gt=item_x, pred=item_j)[0] == 1:
                dumps_xml[item_x] = 1
                dumps_json[item_j] = 1
                #TODO: support 10K classes
                if item_j[1] == item_x[1] == classes[0]:
                    button_measures[0] += 1
                elif item_j[1] == item_x[1] == classes[1]:
                    checkbox_measures[0] += 1
                elif item_j[1] == item_x[1] == classes[2]:
                    date_picker_measures[0] += 1
                elif item_j[1] == item_x[1] == classes[3]:
                    radio_measures[0] += 1
                elif item_j[1] == item_x[1] == classes[4]:
                    text_field_measures[0] += 1
                elif item_j[1] == item_x[1] == classes[5]:
                    select_measures[0] += 1
                else:
                    class_list_dict[item_x[1]][1] += 1
#print "mAP_centers counting finished."

for key in dumps_json.keys():
    if dumps_json[key] == 0:
        #print key
        label = key[1]
        class_list_dict[label][2] += 1
for key in dumps_xml.keys():
    if dumps_xml[key] == 0:
        #print key
        label = key[1]
        class_list_dict[label][2] += 1

AP_list = []
for z in class_list_dict.keys():
    TP = class_list_dict[z][0]
    FP = class_list_dict[z][1]
    TN = class_list_dict[z][2]
    #print "TP - {}".format(TP)
    #print "FP - {}".format(FP)
    #print "TN - {}".format(TN)
    if TP != 0 or FP != 0 or TN != 0:
        AP = float(TP)/(TP+FP+TN)
        class_list_dict[z].append(AP)
        AP_list.append(AP)
centre_mAP = np.mean(AP_list)

bool_center = args.center
bool_iou = args.iou
if bool_center:
    print "Centre_score = {}".format(centre_mAP)
if bool_iou:
    print "IOU_mAP = {}".format(IOU_mAP)

if not (bool_iou or bool_center):
    print "You didn't set any flag. Run precise.py -h for more information"
