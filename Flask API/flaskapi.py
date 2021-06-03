from tensorflow.keras.preprocessing.image import img_to_array
from tensorflow.keras.applications import imagenet_utils
import tensorflow as tf
from PIL import Image
import numpy as np
import flask
import io
import json
import requests
import os

app = flask.Flask(__name__)

#Load modelnya
model = tf.keras.models.load_model('cataract_model_basic.h5')

#Directory untuk save image yang di upload via webpagenya
app.config['UPLOAD_FOLDER'] = "/home/c0050413/capstone-mlmodel/images/"

#Ambil image yang di upload kemudian di save ke folder /images/ dan kemudian di gunakan untuk kirim POST request ke diri sendirinya
@app.route("/upload-image", methods=["GET", "POST"])
def upload_file():
    if flask.request.files:
        image = flask.request.files["file"]
        path = os.path.join(app.config['UPLOAD_FOLDER'], image.filename)
        image.save(path)
        images = open(path, "rb").read()
        payload = {"image": images}
        r = requests.post("http://35.225.123.148:5000", files=payload).json()
        return(r)
    return flask.render_template("webpage.html")

#Tampilin initial webpage
@app.route('/',methods=['GET'])
def HelloWorld():
    return flask.render_template("webpage.html")

#Convert Image resolution
def convert_image(image):
    if image.mode != "RGB":
        image = image.convert("RGB")
    
    image = image.resize((256,256))
    image = img_to_array(image)
    image = np.expand_dims(image, axis=0)
    image = image/255.
    image = imagenet_utils.preprocess_input(image)

    return image

#Predict
@app.route('/',methods=['POST'])
def predict():
    image = flask.request.files["image"].read()
    image = Image.open(io.BytesIO(image))
    image = convert_image(image)

    prediction = model.predict(image)
    print(prediction)

    if(float(prediction) >= 0.5):
        return json.dumps("Cataract")
    else:
        return json.dumps("Normal")

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
