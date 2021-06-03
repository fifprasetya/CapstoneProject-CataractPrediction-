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

#Tampilin initial webpage
@app.route('/',methods=['GET'])
def HelloWorld():
#Tampilkan webpage
    return flask.render_template("webpage.html")

#Convert Image resolution
def convert_image(image):
    #convert ke RGB (3x8-bit pixels, true color)
    image = image.convert("RGB")
    #Resize gambarnya ke resolusi yang model terima (256x256)
    image = image.resize((256,256))
    #Convert imagenya dari PIL format jadi NumPy array
    image = img_to_array(image)
    #Expand arraynya 
    image = np.expand_dims(image, axis=0)
    #Return image
    return image

#Predict
@app.route('/',methods=['POST'])
def predict():
    #Terima Gambar dari POST Request kemudian di baca sebagai bytes
    image = flask.request.files["image"].read()
    #buka dan identify gambarnya sebagai Binary data
    image = Image.open(io.BytesIO(image))
    #Convert gambarnya dengan menggunakan function di atas
    image = convert_image(image)
    #Predict gambarnya
    prediction = model.predict(image)
    #Print hasil prediction di flask api (di gunakan buat saya cek hasil) (kalau tidak mau bisa di comment aja
    print(prediction)
    #Kata team ML model outputnya 0 sampai 1? terus katanya yang 0.5 keatas di jadiin Cataract jadi hasil 0.5 keatas saya return hasilnya sebagai Cataract karena jika tidak nanti outputnya 0 atau 1
    if(float(prediction) >= 0.5):
        #return output sebagai json
        return json.dumps("Cataract")
    #Else output Normal
    else:
        return json.dumps("Normal")

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
