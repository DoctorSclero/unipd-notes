import threading
from http.server import HTTPServer, BaseHTTPRequestHandler

class Sito1Handler(BaseHTTPRequestHandler):
    def log_message(self, format, *args):
        # Suppress logging to keep output clean
        pass

    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-Type", "text/plain; charset=utf-8")
        self.end_headers()
        self.wfile.write("sono www.sito1.it\n".encode("utf-8"))

class Sito2Handler(BaseHTTPRequestHandler):
    def log_message(self, format, *args):
        # Suppress logging to keep output clean
        pass

    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-Type", "text/plain; charset=utf-8")
        self.end_headers()
        self.wfile.write("sono www.sito2.it\n".encode("utf-8"))

def run_server(port, handler_class):
    server_address = ('127.0.0.1', port)
    httpd = HTTPServer(server_address, handler_class)
    print(f"Starting server on port {port}...")
    httpd.serve_forever()

if __name__ == "__main__":
    t1 = threading.Thread(target=run_server, args=(8888, Sito1Handler), daemon=True)
    t2 = threading.Thread(target=run_server, args=(8889, Sito2Handler), daemon=True)
    
    t1.start()
    t2.start()
    
    try:
        # Keep main thread alive
        import time
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        print("\nStopping servers...")
